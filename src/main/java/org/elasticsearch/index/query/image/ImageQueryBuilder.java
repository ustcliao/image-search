package org.elasticsearch.index.query.image;


import org.apache.lucene.search.Query;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import net.semanticmetadata.lire.imageanalysis.features.LireFeature;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoostingQueryBuilder;

import java.io.IOException;
import java.util.Objects;

public class ImageQueryBuilder extends AbstractQueryBuilder<ImageQueryBuilder> {

    public static final String NAME = "img";
    
    protected static final ParseField FEATURE_FIELD = new ParseField("feature");
    protected static final ParseField IMAGE_FIELD = new ParseField("image");
    protected static final ParseField HASH_FIELD = new ParseField("hash");
    protected static final ParseField BOOST_FIELD = new ParseField("boost");
    protected static final ParseField LIMIT_FIELD = new ParseField("limit");


    private String featureTerm;
    private String imageTerm;
    private String hashTerm;
    private float boostTerm;
    private int limitTerm;


    private String fieldName;

    private String feature;
    
    private LireFeature lireFeature;

    private byte[] image;

    private String hash;

    private float boost = -1;

    private int limit = -1;

    private String lookupIndex;

    private String lookupType;

    private String lookupId;

    private String lookupRouting;

    private String lookupPath;
    
    public ImageQueryBuilder() {
        
    }
    
    public ImageQueryBuilder(StreamInput in) throws IOException {
        System.out.println("ImageQueryBuilder------");
        fieldName = in.readString();
        featureTerm = in.readString();
        imageTerm = in.readString();
    }
    
    @Override
    protected void doWriteTo(StreamOutput out) throws IOException {
      System.out.println("doWriteTo------");
      out.writeString(fieldName);
      out.writeString(featureTerm);
      out.writeString(imageTerm);
    }

    @Override
    protected void doXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject(ImageQueryParser.NAME);

        builder.startObject(fieldName);
        builder.field("feature",  "CEDD");

        if (imageTerm != null) {
            builder.field("image", imageTerm);
        }

        builder.endObject();
        builder.endObject();
    }

    @Override
    protected Query doToQuery(QueryShardContext context) throws IOException {
        System.out.println(fieldName + "------ " + imageTerm);
        return new ImageQuery(fieldName, imageTerm, boost);
    }
    
    @Override
    protected boolean doEquals(ImageQueryBuilder other) {
        if (!Objects.equals(fieldName, other.fieldName)) {
            return false;
          }
          if (!Objects.equals(feature, other.feature)) {
            return false;
          }
          if (!Objects.equals(image, other.image)) {
              return false;
            }
          return true;
    }
    
    @Override
    public String getWriteableName() {
        // TODO Auto-generated method stub
        return NAME;
    }

    @Override
    protected int doHashCode() {
        // TODO Auto-generated method stub
        return Objects.hash(fieldName, feature, image);
    }

    
    public ImageQueryBuilder(String fieldName, String image, float boost) {
        //super(fieldName);
        this.fieldName = fieldName;
        this.imageTerm = image;
        this.boost = boost;
    }

    public ImageQueryBuilder feature(String feature) {
        this.feature = feature;
        return this;
    }

    public ImageQueryBuilder image(byte[] image) {
        this.image = image;
        return this;
    }

    public ImageQueryBuilder hash(String hash) {
        this.hash = hash;
        return this;
    }

    public ImageQueryBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public ImageQueryBuilder lookupIndex(String lookupIndex) {
        this.lookupIndex = lookupIndex;
        return this;
    }

    public ImageQueryBuilder lookupType(String lookupType) {
        this.lookupType = lookupType;
        return this;
    }

    public ImageQueryBuilder lookupId(String lookupId) {
        this.lookupId = lookupId;
        return this;
    }

    public ImageQueryBuilder lookupPath(String lookupPath) {
        this.lookupPath = lookupPath;
        return this;
    }

    public ImageQueryBuilder lookupRouting(String lookupRouting) {
        this.lookupRouting = lookupRouting;
        return this;
    }

}
