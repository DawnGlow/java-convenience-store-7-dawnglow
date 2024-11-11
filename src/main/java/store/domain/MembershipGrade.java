package store.domain;

public enum MembershipGrade {
    MEMBER(30), NONMEMBER(0);

    private final int discountPercent;
    private static final int MAX_DISCOUNT_PRICE = 8000;

    MembershipGrade(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    /*
    멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
    프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
    멤버십 할인의 최대 한도는 8,000원이다.
     */

    public int getDiscountPrice(int price) {
        int originDiscountPrice = price * discountPercent / 100;
        return Math.min(originDiscountPrice, MAX_DISCOUNT_PRICE);
    }
}
