package ru.netology.pageobject.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.pageobject.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
        private SelenideElement heading = $("[data-test-id=dashboard]");
        private ElementsCollection cards = $$(".list__item div");
        private final String balanceStart = "баланс: ";
        private final String balanceFinish = " р.";

        public DashboardPage() {
            heading.shouldBe(visible);
        }

        public int getCardBalance(DataHelper.CardInfo cardInfo) {
            var text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(12, 16))).getText();
            return extractBalance(text);
        }

        private int extractBalance(String text) {
            val start = text.indexOf(balanceStart);
            val finish = text.indexOf(balanceFinish);
            val value = text.substring(start + balanceStart.length(), finish);
            return Integer.parseInt(value);
        }

        public TransferPage selectCard(DataHelper.CardInfo cardInfo) {
            cards.findBy(attribute("data-test-id", cardInfo.getTestId())).$("button").click();
            return new TransferPage();
        }
    }
