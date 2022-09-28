package io.srinnix.gccomment.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RegexUtilsTest {

    @Test
    public void testExtractUrl() {
        Assert.assertNull(RegexUtils.extractUrl(null));
        Assert.assertNull(RegexUtils.extractUrl(""));
        Assert.assertNull(RegexUtils.extractUrl("this is a plain text"));

        String url1 = "www.abc.com";
        List<String> urls1 = RegexUtils.extractUrl("this is an url: " + url1);
        Assert.assertEquals(urls1.get(0), url1);

        String url2 = "http://abc.com";
        List<String> urls2 = RegexUtils.extractUrl("this is an url: " + url2);
        Assert.assertEquals(urls2.get(0), url2);

        String url3 = "https://abc.com";
        List<String> strings = RegexUtils.extractUrl("this is an url: " + url3);
        Assert.assertEquals(strings.get(0), url3);

        String url4 = "www://abc.com";
        List<String> urls4 = RegexUtils.extractUrl("this is an invalid url: " + url4);
        Assert.assertNull(urls4);
    }

    @Test
    public void testExtractTag() {
        Assert.assertNull(RegexUtils.extractTag(null));
        Assert.assertNull(RegexUtils.extractTag(""));
        Assert.assertNull(RegexUtils.extractTag("this is a plain text"));
        Assert.assertNull(RegexUtils.extractTag("@ abc"));
        Assert.assertNull(RegexUtils.extractTag("@1 abc"));
        Assert.assertNull(RegexUtils.extractTag("@+ abc"));

        String tag = "@elonmusk";
        List<String> tags = RegexUtils.extractTag("this is a tag: " + tag);
        Assert.assertEquals(tags.get(0), tag);
    }
}
