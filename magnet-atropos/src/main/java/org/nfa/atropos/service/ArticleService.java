package org.nfa.atropos.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.nfa.atropos.model.Article;
import org.nfa.base.ApplicationException;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ArticleService {

	private final Map<String, Article> data = new ConcurrentHashMap<>();

	public Flux<Article> list() {
		return Flux.fromIterable(data.values());
	}

	public Flux<Article> getById(Flux<String> ids) {
		return ids.flatMap(id -> Mono.justOrEmpty(data.get(id)));
	}

	public Mono<Article> getById(String id) {
		return Mono.justOrEmpty(data.get(id)).switchIfEmpty(Mono.error(new ApplicationException("not found", 10010)));
	}

	public Mono<Article> createOrUpdate(Article Article) {
		data.put(Article.getId(), Article);
		return Mono.just(Article);
	}

	public Mono<Article> delete(String id) {
		return Mono.justOrEmpty(data.remove(id));
	}

}
