package cz.bouda.spring.reactor;

import cz.bouda.spring.reactor.domain.JokeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.event.Event;
import reactor.function.Consumer;

import java.util.concurrent.CountDownLatch;

@Service
public class Receiver implements Consumer<Event<Integer>> {

    @Autowired
    CountDownLatch latch;

    RestTemplate restTemplate = new RestTemplate();

    public void accept(Event<Integer> ev) {
        JokeResource jokeResource = restTemplate.getForObject("http://api.icndb.com/jokes/random", JokeResource.class);
        System.out.println("Joke " + ev.getData() + ": " + jokeResource.getValue().getJoke());
        latch.countDown();
    }

}