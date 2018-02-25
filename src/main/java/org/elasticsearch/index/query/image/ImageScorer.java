package org.elasticsearch.index.query.image;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.Bits;

public class ImageScorer extends AbstractImageScorer {
    //private int doc = -1;
    private final int maxDoc;
    private final Bits liveDocs;
    private final DocIdSetIterator it;

    ImageScorer(IndexReader reader, Bits liveDocs, Weight w) {
        super(w, "", null, reader, 1);
        this.liveDocs = liveDocs;
        maxDoc = reader.maxDoc();
        System.out.println("############");
        it = null;
    }
    
    ImageScorer(LeafReaderContext context, Weight w) {
        super(context.reader(), w);
        final LeafReader reader = context.reader();
      
        this.liveDocs =  context.reader().getLiveDocs();
        maxDoc = reader.maxDoc();
        
        System.out.println("thread = " + Thread.currentThread() + " context.reader() : " + context.reader().getLiveDocs() + " maxDoc=" + maxDoc );
        it =  new DocIdSetIterator() {
            int doc = -1;

            @Override
            public int docID() {
              return doc;
            }

            @Override
            public int nextDoc() throws IOException {
                System.out.println("thread = " + Thread.currentThread()+ " nextDoc1 = "+ doc);
                doc++;
                while(liveDocs != null && doc < maxDoc && !liveDocs.get(doc)) {
                    doc++;
                }
                if (doc == maxDoc) {
                    doc = NO_MORE_DOCS;
                }
                System.out.println("thread = " + Thread.currentThread() + "nextDoc2 = "+ doc);
                return doc;
            }

            @Override
            public int advance(int target) throws IOException {
                System.out.println("thread = " + Thread.currentThread() +  "advance  target= "+ target);
                doc = target-1;
                return nextDoc();
            }

            @Override
            public long cost() {
                System.out.println("thread = " + Thread.currentThread() +  "cost  maxDoc= "+ maxDoc);
              return maxDoc;
            }
          };
    }

    @Override
    public int docID() {
        return it.docID();
    }

    /*
    public int nextDoc() throws IOException {
        doc++;
        while(liveDocs != null && doc < maxDoc && !liveDocs.get(doc)) {
            doc++;
        }
        if (doc == maxDoc) {
            doc = Integer.MAX_VALUE;
        }
        return doc;
    }


    public int advance(int target) throws IOException {
        doc = target-1;
        return nextDoc();
    }

    public long cost() {
        return maxDoc;
    }
    */

    @Override
    public DocIdSetIterator iterator() {
        // TODO Auto-generated method stub
        // return DocIdSetIterator.all(4);
        // return DocIdSetIterator.empty();
        return it;
    }
}
