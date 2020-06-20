package io.pivotal.quotes.k8s;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.LocalPortForward;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import io.dekorate.testing.annotation.Inject;
import io.dekorate.testing.annotation.KubernetesIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@KubernetesIntegrationTest
public class QuotesK8sTest {
    @Inject
    private KubernetesClient client;

    @Inject
    Pod pod;

    @Test
    void shouldRespondWithHelloWorld() throws Exception {
        Assertions.assertNotNull(client);
        System.out.println("Forwarding port");
        LocalPortForward p = client.pods().withName(pod.getMetadata().getName()).portForward(8080);
        try {
            assertTrue(p.isAlive());
            URL url = new URL("http://localhost:"+p.getLocalPort()+"/");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().get().url(url).build();
            Response response = client.newCall(request).execute();
            assertEquals(response.body().string(), "Hello world");
        } finally {
            p.close();
        }
    }
}
