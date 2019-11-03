package challenge.keywordscore.client;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.stereotype.Component;

@Component
public class AmazonClient {

    public static final String SEARCH_ALIAS_PARAM = "search-alias";
    public static final String SEARCH_ALIAS_VALUE = "aps";
    public static final String CLIENT_PARAM = "client";
    public static final String CLIENT_VALUE = "amazon-search-ui";
    public static final String MKT_PARAM = "mkt";
    public static final String MKT_VALUE = "1";
    public static final String KEYWORD_PARAM = "q";
    public static final String AMAZON_AUTOCOMPLETE_URL = "https://completion.amazon.com/search/complete";
    public static final String ACCEPT_HEADER = "accept";
    public static final String ACCEPT_JSON = "application.propertiesion/json";

    public JsonNode autocomplete(String keyword) {
        // https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q=iphone
        HttpResponse<JsonNode> obj = Unirest.post(AMAZON_AUTOCOMPLETE_URL)
                .header(ACCEPT_HEADER, ACCEPT_JSON)
                .queryString(SEARCH_ALIAS_PARAM, SEARCH_ALIAS_VALUE)
                .queryString(CLIENT_PARAM, CLIENT_VALUE)
                .queryString(MKT_PARAM, MKT_VALUE)
                .queryString(KEYWORD_PARAM, keyword)
                .asJson();

        return obj.getBody();
    }
}
