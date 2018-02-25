package org.elasticsearch.index.query.image;

import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.SortedDocValues;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.BytesRef;
import org.elasticsearch.ElasticsearchImageProcessException;

import net.semanticmetadata.lire.imageanalysis.features.LireFeature;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Calculate score for each image
 * score = (1 / distance) * boost
 */
public abstract class AbstractImageScorer extends Scorer {
    private final String luceneFieldName;
    private final LireFeature lireFeature;
    private final LeafReader reader;
    private final float boost;
    private BinaryDocValues binaryDocValues;
    private SortedDocValues sortedDocValues;

    protected AbstractImageScorer(Weight weight, String luceneFieldName, LireFeature lireFeature, IndexReader reader,
                                  float boost) {
        super(weight);
        this.luceneFieldName = luceneFieldName;
        this.lireFeature = lireFeature;
        this.reader = null;
        this.boost = boost;
    }
    
    protected AbstractImageScorer(LeafReader leafReader, Weight weight) {
        super(weight);
        this.luceneFieldName = null;
        this.lireFeature = null;
        this.reader = leafReader;

        this.boost = 0;
    }

    @Override
    public float score() throws IOException  {
        assert docID() != Integer.MAX_VALUE;
        Document  d = reader.document(docID());
        String name = "my_img";
        d.getValues(name);
        System.out.println("51----"+d.getFields().get(0));
        System.out.println("511----"+d.getFields().get(0).binaryValue());
        System.out.println("52----"+d.getFields().get(0).name());
        //System.out.println("53----"+d.getFields().get(0).stringValue());//null
 
        System.out.println("54----"+d.getFields().get(0).toString());
        System.out.println("55----"+d.getFields().get(0));
        System.out.println("56----"+new String(d.getFields().get(0).binaryValue().bytes));
        System.out.println("6----"+d.getValues(name).length);
        System.out.println("docID():" + docID() + "my_img : " );
        float a = (float) 1.0;
        return a;

        /*
        if (binaryDocValues == null) {
            LeafReader atomicReader = (LeafReader) reader;
            binaryDocValues = atomicReader.getBinaryDocValues(luceneFieldName);
        }

        try {
            BytesRef bytesRef = binaryDocValues.get(docID());
            LireFeature docFeature = lireFeature.getClass().newInstance();
            docFeature.setByteArrayRepresentation(bytesRef.bytes);
            float distance = (float) lireFeature.getDistance(docFeature);
            float score;
            if (Float.compare(distance, 1.0f) <= 0) { // distance less than 1, consider as same image
                score = 2f - distance;
            } else {
                score = 1 / distance;
            }
            return score * boost;
        } catch (Exception e) {
            throw new ElasticsearchImageProcessException("Failed to calculate score", e);
        }
        */
    }

    @Override
    public int freq() {
        return 1;
    }
}
