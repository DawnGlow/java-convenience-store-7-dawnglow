package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.MembershipGrade;

public class InputView {
    public String readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        while (true) {
            try {
                String input = Console.readLine();
                validateReadItemSyntax(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateReadItemSyntax(String input) {
        String regex = "^\\[[^\\[\\]-]+-\\d+\\](,\\[[^\\[\\]-]+-\\d+\\])*$$";
        if (!input.matches(regex)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private void validateYesOrNo(String answer) {
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public boolean promotionStockInefficientMessage(String name, int quantity) {
        System.out.println("현재 " + name + " " + quantity + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        while (true) {
            try {
                String answer = Console.readLine();
                validateYesOrNo(answer);
                return Console.readLine().equals("Y");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public boolean buyMoreForPromotion(String name, int presentableQuantity) {
        System.out.println("현재 " + name + "은(는) " + presentableQuantity + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        while (true) {
            try {
                String answer = Console.readLine();
                validateYesOrNo(answer);
                return Console.readLine().equals("Y");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public MembershipGrade applyMembership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        while (true) {
            try {
                String answer = Console.readLine();
                validateYesOrNo(answer);
                if (answer.equals("Y")) {
                    return MembershipGrade.MEMBER;
                }
                return MembershipGrade.NONMEMBER;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean isContinue() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        while (true) {
            try {
                String answer = Console.readLine();
                validateYesOrNo(answer);
                return answer.equals("Y");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
