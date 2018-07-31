package e2e;

import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class E2eTests extends E2EBase {

    @Test
    public void should_return_pivotal_stock_quote() {

        // @formatter:off
        when().
            get("/quotes/v1/quotes?q=PVTL").
        then().
            statusCode(HttpStatus.SC_OK).
            body("[0].Symbol", Matchers.is("PVTL"));
        // @formatter:on

    }

    @Test
    public void should_create_a_user_account_and_trade() {

        String username = faker.letterify("????????");

        // @formatter:off

        // Create the User
        given().
            body("{\n" +
                    "  \"id\": null,\n" +
                    "  \"address\": \"\",\n" +
                    "  \"passwd\": \"password\",\n" +
                    "  \"userid\": \"" + username + "\",\n" +
                    "  \"email\": \"anon@springsource.com\",\n" +
                    "  \"creditcard\": \"\",\n" +
                    "  \"fullname\": \"John Doe\",\n" +
                    "  \"authtoken\": null,\n" +
                    "  \"creationdate\": \"2018-07-28T01:31:32.123\",\n" +
                    "  \"logoutcount\": null,\n" +
                    "  \"lastlogin\": null,\n" +
                    "  \"logincount\": null\n" +
                    "}").
            contentType(ContentType.JSON).
        when().
            post("/user/users").
        then().
            statusCode(HttpStatus.SC_CREATED);

        // Create an account for the user
        given().
            body("{\n" +
                    "  \"id\": null,\n" +
                    "  \"name\": \"" + faker.letterify("????????") + "\",\n" +
                    "  \"userid\": \"" + username + "\",\n" +
                    "  \"creationdate\": \"2018-07-28T01:31:32.123\",\n" +
                    "  \"openbalance\": 100000.00,\n" +
                    "  \"balance\": 100000.00,\n" +
                    "  \"currency\": \"USD\",\n" +
                    "  \"type\": \"SAVINGS\"\n" +
                    "}").
            contentType(ContentType.JSON).
        when().
            post("/accounts/accounts").
        then().
            statusCode(HttpStatus.SC_CREATED);

        // Retrieve the newly created account id
        Integer accountId = when().
            get("/accounts/accounts?name={name}", username).
        then().
            statusCode(HttpStatus.SC_OK).
        extract().
            path("[0].id");

        // Submit an order
        given()
            .body("{\n" +
                    "  \"userId\": \"" + username + "\",\n" +
                    "  \"accountId\": " + accountId + ",\n" +
                    "  \"symbol\": \"PVTL\",\n" +
                    "  \"orderFee\": 1,\n" +
                    "  \"completionDate\": \"2018-07-28T01:31:32.123\",\n" +
                    "  \"orderType\": \"BUY\",\n" +
                    "  \"price\": 10,\n" +
                    "  \"currency\": \"USD\",\n" +
                    "  \"quantity\": 1000\n" +
                    "}")
            .contentType(ContentType.JSON)
        .when()
            .post("/portfolio/portfolio")
        .then()
            .statusCode(HttpStatus.SC_CREATED);

        // Retrieve the portfolio to verify order was posted
        when().
            get("/portfolio/portfolio/{userId}", username).
        then().
            statusCode(HttpStatus.SC_OK).
            body("holdings.PVTL.orders[0].quantity", is(1000));

        // @formatter:on
    }


}