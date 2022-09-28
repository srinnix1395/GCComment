package io.srinnix.gccomment.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import timber.log.Timber;

public class UrlUtils {
    private static final String AGENT = "Mozilla";
    private static final String REFERRER = "http://www.google.com";
    private static final int TIMEOUT = 10000;
    private static final String DOC_SELECT_QUERY = "meta[property^=og:]";
    private static final String OPEN_GRAPH_KEY = "content";
    private static final String PROPERTY = "property";
    private static final String OG_TITLE = "og:title";


    public static String getPageTitle(String url) {
        try {
            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent(AGENT)
                    .referrer(REFERRER)
                    .timeout(TIMEOUT)
                    .followRedirects(true)
                    .execute();

            Document doc = response.parse();

            Elements ogTags = doc.select(DOC_SELECT_QUERY);
            if (ogTags.size() > 0) {
                for (int i = 0; i < ogTags.size(); i++) {
                    Element tag = ogTags.get(i);
                    String text = tag.attr(PROPERTY);

                    if (text.equals(OG_TITLE)) {
                        return tag.attr(OPEN_GRAPH_KEY);
                    }
                }
            }
        } catch (Exception e) {
            Timber.e(e);
        }

        return null;
    }
}
