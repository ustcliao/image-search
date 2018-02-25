package org.elasticsearch.index.query.image;

import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

import net.semanticmetadata.lire.imageanalysis.features.LireFeature;

import java.io.IOException;
import java.util.Set;


/**
 * Copied from {@link MatchAllDocsQuery}, calculate score for all docs
 */
public class ImageQuery extends Query {

    private String luceneFieldName;
    private String lireFeature;

    public ImageQuery(String luceneFieldName, String lireFeature, float boost) {
        this.luceneFieldName = luceneFieldName;
        this.lireFeature = lireFeature;
        //setBoost(boost);
    }

    @Override
    public Weight createWeight(IndexSearcher searcher, boolean needsScores) throws IOException {
        return new ImageWeight(this, searcher);
    }
    


    public float getBoost() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public String toString(String field) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(luceneFieldName);
        buffer.append(",");
        //buffer.append(lireFeature.getClass().getSimpleName());
        //buffer.append(ToStringUtils.boost(getBoost()));
        return buffer.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImageQuery))
            return false;
        ImageQuery other = (ImageQuery) o;
        return (this.getBoost() == other.getBoost())
                && luceneFieldName.equals(luceneFieldName)
                && lireFeature.equals(lireFeature);
    }

    @Override
    public int hashCode() {
        int result = this.hashCode();
        result = 31 * result + luceneFieldName.hashCode();
        result = 31 * result + lireFeature.hashCode();
        result = Float.floatToIntBits(getBoost()) ^ result;
        return result;
    }
}
