import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import java.util.HashMap;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Main {

    static TreeMap<Integer,String> queries = new TreeMap<>();

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        String indexLocation = ("index"); //define were to store the index

        Directory index = new RAMDirectory(); //create a RAMDirectory

        EnglishAnalyzer analyzer = new EnglishAnalyzer();
        Similarity similarity =  new BM25Similarity();
        IndexWriterConfig config =  new IndexWriterConfig(analyzer);
        config.setSimilarity(similarity);

        IndexWriter writer = new IndexWriter(index, config);

        // Open txt and parse through it with a for and add documents to the writer

        File file = new File("IR2022/documents.txt");
        Scanner scanner
                = new Scanner(file);

        readDocuments(scanner,writer);
        writer.close();

        File queries_file = new File("IR2022/queries.txt");
        Scanner scanner2 = new Scanner(queries_file);

        readQueries(scanner2);

        File f= new File("IR2022/my_results20.txt");
        f.delete();
        f= new File("IR2022/my_results30.txt");
        f.delete();
        f= new File("IR2022/my_results50.txt");
        f.delete();

        /*for(Integer key: queries.keySet()) {
            search("Text", key, queries.get(key),index,analyzer,20);
            search("Text", key,queries.get(key),index,analyzer,30);
            search("Text", key,queries.get(key),index,analyzer,50);
        }*/

        for(Integer key: queries.keySet()) {
            search("Title", key, queries.get(key),index,analyzer,20);
            search("Title", key,queries.get(key),index,analyzer,30);
            search("Title", key,queries.get(key),index,analyzer,50);
        }
        System.out.println("FINISHED");
    }

    private static void search(String field,Integer queryid, String query, Directory index,EnglishAnalyzer analyzer, int hitsPerPage) throws IOException, ParseException, InterruptedException {

        query = QueryParser.escape(query); // used to interpret slashes
        Query q = new QueryParser(field,analyzer).parse(query);

        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);

        TopScoreDocCollector collector =  TopScoreDocCollector.create(hitsPerPage);
        searcher.search(q,collector);

        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        if(hitsPerPage==20) {
            writetoFile(20, hits, queryid, searcher);
        }
        else if(hitsPerPage==30){
            writetoFile(30, hits, queryid, searcher);
        }
        else if(hitsPerPage==50){
            writetoFile(50, hits, queryid, searcher);
        }

        reader.close();

    }


    public static void writetoFile(int type, ScoreDoc[] hits,Integer queryid,IndexSearcher searcher){
        String Q = "Q0";
        String q = "Q";
        String s;
        if(type==20) {
            try {
                File myObj = new File("IR2022/my_results20.txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                FileWriter myWriter = new FileWriter("IR2022/my_results20.txt", true);
                BufferedWriter bw = new BufferedWriter(myWriter);
                for (ScoreDoc hit : hits) {
                    int docId = hit.doc;
                    Document d = searcher.doc(docId);
                    if (queryid<10)
                        s = Q;
                    else
                        s = q;
                    bw.write(s+queryid + " " + 0 + " " + d.get("Id") + " " + 0 + " " + hit.score + " " + "myIRmethod");
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        else if(type==30){
            try {
                File myObj = new File("IR2022/my_results30.txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                FileWriter myWriter = new FileWriter("IR2022/my_results30.txt", true);
                BufferedWriter bw = new BufferedWriter(myWriter);
                for (ScoreDoc hit : hits) {
                    int docId = hit.doc;
                    Document d = searcher.doc(docId);
                    if (queryid<10)
                        s = Q;
                    else
                        s = q;
                    bw.write(s+queryid + " " + 0 + " " + d.get("Id") + " " + 0 + " " + hit.score + " " + "myIRmethod");
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        else if(type==50){
            try {
                File myObj = new File("IR2022/my_results50.txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                FileWriter myWriter = new FileWriter("IR2022/my_results50.txt", true);
                BufferedWriter bw = new BufferedWriter(myWriter);

                for (ScoreDoc hit : hits) {
                    int docId = hit.doc;
                    Document d = searcher.doc(docId);
                    if (queryid<10)
                        s = Q;
                    else
                        s = q;
                    bw.write(s+queryid + " " + 0 + " " + d.get("Id") + " " + 0 + " " + hit.score + " " + "myIRmethod");
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        }



    private static void readQueries(Scanner scanner) throws IOException, InterruptedException {

        int linecount = 0;
        int end = 0;
        String id = null;
        String queryContent = null;
        String endline = null;
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            linecount++;
            if (linecount == 1){
                id = line;
            }
            else if (linecount == 2){
                queryContent = line;
            }
            else{
                if (end == 0){
                    end =1;
                    endline = line;
                }
                if(line.equals(endline)){

                    queryContent = queryContent.toLowerCase();
                    id = id.replace("Q","");
                    int intid = Integer.parseInt(id);
                    queries.put(intid,queryContent);
                    linecount=0;
                }
                else{
                    queryContent = queryContent + line;
                }
            }
        }
    }

    private static void readDocuments(Scanner scanner, IndexWriter writer) throws IOException {
        int linecount = 0;
        int end = 0;
        String id = null;
        String title = null;
        String firstpart = null;
        String endline = null;

        while(scanner.hasNextLine()){

            String line = scanner.nextLine();

            linecount+=1;
            if(linecount==1){ // id
                id = line;
            }
            else if(linecount==2){ // title and some of the text
                String[] currentline = line.split(":");
                title = currentline[0];
                firstpart = currentline[1];
            }
            else{
                if(end==0){
                    end =1;
                    endline = line;
                }
                if(line.equals(endline)){
                    addDoc(writer,id, title, firstpart);
                    linecount=0;
                }
                else{
                    firstpart = firstpart + line;
                }

            }


        }
        System.out.println("We have inserted: " + writer.numDocs() + " documents");
    }


    private static void addDoc(IndexWriter writer, String id, String title, String value) throws IOException{
        Document doc = new Document();
        TextField identifier = new TextField("Id",id,Field.Store.YES);
        TextField txtitle = new TextField("Title",title,Field.Store.YES);
        TextField text = new TextField("Text",value,Field.Store.NO);
        doc.add(identifier);
        doc.add(txtitle);
        doc.add(text);
        writer.addDocument(doc);
    }

}
