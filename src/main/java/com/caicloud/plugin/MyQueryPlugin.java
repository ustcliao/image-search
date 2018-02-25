package com.caicloud.plugin;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.image.ImageQueryBuilder;
import org.elasticsearch.index.query.image.ImageQueryParser;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.SearchPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MyQueryPlugin extends Plugin implements SearchPlugin{
  @Override
  public List<QuerySpec<?>> getQueries() {
    ImageQueryParser queryParser = new ImageQueryParser(null);
    return Collections.singletonList(new QuerySpec<QueryBuilder>(ImageQueryBuilder.NAME,
        ImageQueryBuilder::new,
        parseContext -> (Optional<QueryBuilder>) (Optional) queryParser.fromXContent(parseContext)));
  }
}
