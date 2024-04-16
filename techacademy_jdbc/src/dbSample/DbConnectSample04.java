package dbSample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.xml.crypto.dsig.keyinfo.KeyInfo;


public class DbConnectSample04 {

    public static void main(String[] args) {
        
        //データベース接続と結果取得のための変数
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
     try {
            // 1. ドライバーのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. DBと接続する
             con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Sorauehara0130!!"
                    );
             
             
             String sql = "SELECT * FROM country WHERE name = ?";
             
         // 3. DBとやりとりする窓口（Statementオブジェクト）の作成
         pstmt = con.prepareStatement(sql);
         
         // 4, 5. Select文の実行と結果を格納／代入
         System.out.print("検索キーワード入力してください>");
         String input = keyIn();
         
        
         pstmt.setString(1,input);
         rs = pstmt.executeQuery();
           
           // 6. 結果を表示する
            while( rs.next() ) {
             // Name列の値を取得
                String name = rs.getString("Name");
//            // Population列の値を取得 　← 追記
                int population = rs.getInt("Population");  // ← 追記
                // 取得した値を表示A
                System.out.println(name);
                System.out.println(population);   // ← 追記
            }
           
                
         }catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバーのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        }finally {
            // 7. 接続を閉じる
            if( rs != null ){
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
           if(pstmt != null) {
               try {
                   pstmt.close();
               }catch(SQLException e) {
                   e.printStackTrace();
               }
           }
           if(con != null) {
               try {
                   con.close();
               }catch (SQLException e) {
                   System.err.println("データベース切断時にエラーが発生しました。");
                   e.printStackTrace();
               }
           }
        }
     
    }

    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        }catch (IOException e) {
            
            
        }
        return line;
        }
}
            
       
    
        

