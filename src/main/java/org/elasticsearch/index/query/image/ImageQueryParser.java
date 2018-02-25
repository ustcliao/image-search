package org.elasticsearch.index.query.image;


import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.elasticsearch.ElasticsearchImageProcessException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.inject.Inject;
 
import org.elasticsearch.common.xcontent.XContentParser;

import org.elasticsearch.index.mapper.image.FeatureEnum;
import org.elasticsearch.index.mapper.image.HashEnum;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryParseContext;
import org.elasticsearch.index.query.QueryParser;
import org.elasticsearch.index.query.QueryValidationException;

import net.semanticmetadata.lire.imageanalysis.features.LireFeature;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

public class ImageQueryParser implements QueryParser<ImageQueryBuilder> {

    public static final String NAME = "img";

    private Client client;

    @Inject
    public ImageQueryParser(Client client) {
        this.client = client;
    }

    //@Override
    public String[] names() {
        return new String[] {NAME};
    }

    @Override
    public Optional<ImageQueryBuilder> fromXContent(QueryParseContext parseContext) throws IOException {
        XContentParser parser = parseContext.parser();
        XContentParser.Token token = parser.nextToken();


        String fieldName = parser.currentName();
        FeatureEnum featureEnum = null;
        //byte[] image = null;
        String image = null;
        HashEnum hashEnum = null;
        float boost = 1.0f;
        int limit = -1;
        String featureValue = null;

        //String lookupIndex = parseContext.index().name();
        String lookupIndex = null;
        String lookupType = null;
        String lookupId = null;
        String lookupPath = null;
        String lookupRouting = null;

        token = parser.nextToken();
        System.out.println("---------------------"+ token.toString());
        if (token == XContentParser.Token.START_OBJECT) {
            System.out.println("start object " + token.isValue());
            String currentFieldName = null;
            while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {
                if (token == XContentParser.Token.FIELD_NAME) {
                    currentFieldName = parser.currentName();
                    System.out.println( "currentFieldName =" +currentFieldName + " value=" + token.isValue());
                } else {
                    System.out.println( "currentFieldName =" +currentFieldName + " value=" + parser.text());
                    if ("feature".equals(currentFieldName)) {
                        featureEnum = FeatureEnum.getByName(parser.text());
                    } else if ("image".equals(currentFieldName)) {
                        image = parser.text();
                    } else {
                        //throw new QueryParsingException(parseContext.index(), "[image] query does not support [" + currentFieldName + "]");
                    }
                }
            }
            parser.nextToken();
        }

        if (featureEnum == null) {
            //throw new QueryParsingException(parseContext.index(), "No feature specified for image query");
        }

        //String luceneFieldName = fieldName + "." + featureEnum.name();
        String luceneFieldName = "my_img";
        return Optional.of(new ImageQueryBuilder(luceneFieldName, image, boost));
    }
}
