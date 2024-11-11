package store.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import store.domain.MembershipGrade;
import store.dto.CategorizedOrderItems;
import store.dto.OrderItemDto;
import store.dto.OrderResponseDto;
import store.exception.InsufficientStockException;
import store.service.ItemService;
import store.service.OrderService;
import store.service.PromotionItemService;
import store.utils.OrderParser;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final OrderService orderService;
    private final PromotionItemService promotionItemService;
    private final ItemService itemService;

    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(OrderService orderService, PromotionItemService promotionItemService,
                           ItemService itemService,
                           InputView inputView, OutputView outputView) {
        this.orderService = orderService;
        this.promotionItemService = promotionItemService;
        this.itemService = itemService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void showInitialMessage() {
        outputView.showWelcomeMessage();
        List<String> totalInformation = getTotalInformation();
        Collections.sort(totalInformation);
        outputView.showItems(totalInformation);
    }

    private List<String> getTotalInformation() {
        return new java.util.ArrayList<>(Stream.of(
                        itemService.findAllInformation(),
                        promotionItemService.findAllInformation())
                .flatMap(List::stream)
                .toList());
    }

    public void orderProcess() {
        while (true) {
            try {
                processSingleOrder();
                if (!inputView.isContinue()) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                handleException(e);
            }
        }
    }

    private void processSingleOrder() throws InsufficientStockException {
        showInitialMessage();
        List<OrderItemDto> orderItemDtos;
        while (true) {
            try {
                orderItemDtos = parseOrderInput();
                validateOrder(orderItemDtos);
                validateTotalOrderItemsStock(orderItemDtos);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        CategorizedOrderItems categorizedOrderItems = categorizeOrderItems(orderItemDtos);
        validateNormalOrderItemsStock(categorizedOrderItems.getNormalOrderItemDtos());
        MembershipGrade membershipGrade = inputView.applyMembership();
        OrderResponseDto orderResponseDto = orderService.processOrder(orderItemDtos, categorizedOrderItems,
                membershipGrade);
        outputView.showOrderResult(orderResponseDto);
    }

    private void validateOrder(List<OrderItemDto> orderItemDtos) {
        List<String> allNames = itemService.findItemNames();
        for (OrderItemDto orderItemDto : orderItemDtos) {
            if (!allNames.contains(orderItemDto.getName())) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
        }
    }

    private void handleException(Exception e) {
        System.out.println(e.getMessage());
    }

    private void validateTotalOrderItemsStock(List<OrderItemDto> orderItemDtos) throws InsufficientStockException {
        for (OrderItemDto orderItemDto : orderItemDtos) {
            int normalItemStock = itemService.findStockByName(orderItemDto);
            int promotionItemStock = promotionItemService.findStockByName(orderItemDto);
            if (normalItemStock + promotionItemStock < orderItemDto.getQuantity()) {
                throw new InsufficientStockException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    private void validateNormalOrderItemsStock(List<OrderItemDto> normalOrderItemDtos)
            throws InsufficientStockException {
        for (OrderItemDto orderItemDto : normalOrderItemDtos) {
            if (!itemService.isSufficientStock(orderItemDto)) {
                throw new InsufficientStockException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    private List<OrderItemDto> parseOrderInput() {
        String input = inputView.readItem();
        return OrderParser.parseToOrderItemDtos(input);
    }

    private CategorizedOrderItems categorizeOrderItems(List<OrderItemDto> orderItemDtos) {
        CategorizedOrderItems categorized = new CategorizedOrderItems();

        for (OrderItemDto orderItemDto : orderItemDtos) {
            if (!promotionItemService.canApplyPromotionNow(orderItemDto)) {
                categorized.addNormalItem(orderItemDto);
                continue;
            }

            if (promotionItemService.isInsufficientStockForPromotion(orderItemDto)) {
                handleInsufficientPromotionStock(orderItemDto, categorized);
                continue;
            }

            handleSufficientPromotionStock(orderItemDto, categorized);
        }

        return categorized;
    }

    private void handleInsufficientPromotionStock(OrderItemDto orderItemDto,
                                                  CategorizedOrderItems categorizedOrderItems) {
        int quantityToNotApplyPromotion = promotionItemService.getQuantityToNotApplyPromotion(orderItemDto);
        boolean addPromotion = inputView.promotionStockInefficientMessage(orderItemDto.getName(),
                quantityToNotApplyPromotion);

        if (!addPromotion) {
            int promotionApplyQuantity = promotionItemService.getQuantityToApplyPromotion(orderItemDto);
            if (promotionApplyQuantity > 0) {
                categorizedOrderItems.addPromotionItem(
                        new OrderItemDto(orderItemDto.getName(), promotionApplyQuantity));
            }
            orderItemDto.setQuantity(promotionApplyQuantity);
            return;
        }

        categorizedOrderItems.addNormalItem(new OrderItemDto(orderItemDto.getName(), quantityToNotApplyPromotion));
        categorizedOrderItems.addPromotionItem(new OrderItemDto(orderItemDto.getName(),
                orderItemDto.getQuantity() - quantityToNotApplyPromotion));
    }

    private void handleSufficientPromotionStock(OrderItemDto orderItemDto,
                                                CategorizedOrderItems categorizedOrderItems) {
        int leftQuantityForPromotion = promotionItemService.getLeftQuantityForPromotion(orderItemDto);
        int presentableQuantityIfBuyMore = promotionItemService.getPresentableQuantityIfBuyMore(orderItemDto);

        boolean buyMore = false;
        if (leftQuantityForPromotion > 0) {
            buyMore =
                    inputView.buyMoreForPromotion(orderItemDto.getName(), presentableQuantityIfBuyMore);
        }

        if (buyMore) {
            orderItemDto.setQuantity(orderItemDto.getQuantity() + leftQuantityForPromotion);
            categorizedOrderItems.addPromotionItem(orderItemDto);
            return;
        }

        categorizedOrderItems.addNormalItem(new OrderItemDto(orderItemDto.getName(), leftQuantityForPromotion));
        categorizedOrderItems.addPromotionItem(new OrderItemDto(orderItemDto.getName(),
                promotionItemService.getQuantityToApplyPromotion(orderItemDto)));
    }
}
