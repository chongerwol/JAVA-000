package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.Random;

public class HttpEndpointRouterImpl implements HttpEndpointRouter {

    @Override
    public String route(List<String> endpoints) {
        int size = endpoints.size();
        Random random = new Random();
        int nextInt = random.nextInt(size);
        return endpoints.get(nextInt);
    }

}
