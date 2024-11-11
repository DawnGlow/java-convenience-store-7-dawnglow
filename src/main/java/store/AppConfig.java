package store;

import store.controller.InitController;
import store.controller.StoreController;
import store.repository.ItemRepository;
import store.repository.PromotionItemRepository;
import store.repository.PromotionRepository;
import store.service.ItemService;
import store.service.OrderService;
import store.service.PromotionItemService;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {
    private final ItemRepository itemRepository = new ItemRepository();

    private final PromotionItemRepository promotionItemRepository = new PromotionItemRepository();

    private final PromotionRepository promotionRepository = new PromotionRepository();

    
    public InitController initController() {
        return new InitController(itemRepository, promotionItemRepository, promotionRepository);
    }

    public OrderService orderService() {
        return new OrderService(itemRepository, promotionItemRepository, promotionRepository);
    }

    public ItemService itemService() {
        return new ItemService(itemRepository);
    }

    public PromotionItemService promotionItemService() {
        return new PromotionItemService(promotionItemRepository);
    }

    public StoreController storeController() {
        return new StoreController(orderService(), promotionItemService(), itemService(), new InputView(),
                new OutputView());
    }

}
