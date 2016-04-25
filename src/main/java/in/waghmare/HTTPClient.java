package in.waghmare;
import io.parallec.core.FilterRegex;
import io.parallec.core.ParallecResponseHandler;
import io.parallec.core.ParallelClient;
import io.parallec.core.ResponseOnSingleTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.util.Asserts;

public class HTTPClient {

    public static void main(String[] args) {

        ParallelClient pc = new ParallelClient();

        Map<String, Object> responseContext = new HashMap<String, Object>();
        responseContext.put("temp", null);

        pc.prepareHttpGet("/userdata/sample_weather_$ZIP.txt")
                .setReplaceVarMapToSingleTargetSingleVar("ZIP",
                    Arrays.asList("95037","48824"), "www.parallec.io")
                .setResponseContext(responseContext)
                .execute(new ParallecResponseHandler() {
                    public void onCompleted(ResponseOnSingleTask res,
                            Map<String, Object> responseContext) {
                        String temp = new FilterRegex("(.*)").filter(res
                                .getResponseContent());
                        System.out.println("\n!!Temperature: " + temp
                                + " TargetHost: " + res.getHost());
                        responseContext.put("temp", temp);
                    }
                });

        int tempGlobal = Integer.parseInt((String) responseContext.get("temp"));
        Asserts.check(
                tempGlobal <= 100 && tempGlobal >= 0,
                " Fail to extract output from sample weather API. Fail different request to same server test");

        pc.releaseExternalResources();
	}
}
