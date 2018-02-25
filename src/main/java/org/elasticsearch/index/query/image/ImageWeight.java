package org.elasticsearch.index.query.image;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.Bits;

public class ImageWeight extends Weight {
    protected ImageWeight(Query query, IndexSearcher searcher) {
        super(query);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "weight(" + this + ")";
    }


    public float getValueForNormalization() {
        return 1f;
    }


    public void normalize(float queryNorm, float topLevelBoost) {
    }

    @Override
    public Scorer scorer(LeafReaderContext context) throws IOException {
        // TODO Auto-generated method stub
        return new ImageScorer(context, this);
    }
    
    /*
    public Scorer scorer(LeafReaderContext context, Bits acceptDocs) throws IOException {
        return new ImageScorer(context.reader(), acceptDocs, this);
    }
    */

    //@Override
    public Explanation explain(LeafReaderContext context, int doc) throws IOException {
        /*
        Scorer scorer = scorer(context, context.reader().getLiveDocs());
        if (scorer != null) {
            int newDoc = scorer.advance(doc);
            if (newDoc == doc) {
                float score = scorer.score();
                ComplexExplanation result = new ComplexExplanation();
                result.setDescription("ImageQuery, product of:");
                result.setValue(score);
                if (getBoost() != 1.0f) {
                    result.addDetail(new Explanation(getBoost(),"boost"));
                    score = score / getBoost();
                }
                result.addDetail(new Explanation(score ,"image score (1/distance)"));
                result.setMatch(true);
                return result;
            }
        }

        return new ComplexExplanation(false, 0.0f, "no matching term");
        */
        throw new IOException();
    }

    @Override
    public void extractTerms(Set<Term> terms) {
        // TODO Auto-generated method stub
        
    }
}