package jp.alhinc.calculate_sales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateSales {

	// 支店定義ファイル名
	private static final String FILE_NAME_BRANCH_LST = "branch.lst";

	// 支店別集計ファイル名
	private static final String FILE_NAME_BRANCH_OUT = "branch.out";

	// エラーメッセージ
	private static final String UNKNOWN_ERROR = "予期せぬエラーが発生しました";
	private static final String FILE_NOT_EXIST = "支店定義ファイルが存在しません";
	private static final String FILE_INVALID_FORMAT = "支店定義ファイルのフォーマットが不正です";

	/**
	 * メインメソッド
	 *
	 * @param コマンドライン引数
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) {
		// 支店コードと支店名を保持するMap
		Map<String, String> branchNames = new HashMap<>();
		// 支店コードと売上金額を保持するMap
		Map<String, Long> branchSales = new HashMap<>();


		 //支店定義ファイルの読み込み処理
	    if(!readFile(args[0], FILE_NAME_BRANCH_LST, branchNames,branchSales)) {
	    	return ;
	    }


		// ※ここから集計処理を作成してください。(処理内容2-1、2-2)
		//listFilesを使用してfilesという配列に、
		//指定したパスに存在する全てのファイル(または、ディレクトリ)の情報を格納します。
 		File[] files = new File(args[0]).listFiles();
		//filesの数だけ繰り返すことで、
		//指定したパスに存在する全てのファイル(または、ディレクトリ)の数だけ繰り返されます。
       //今まで出てこなかったものをここで宣言して使えるようにする。

 		List<File> rcdFiles = new ArrayList<>();


		for(int i = 0; i < files.length ; i++) {
			//files[i].getName() でファイル名が取得できます
			String fileName =files[i].getName();


			//ファイルの条件にあてはまるものだけ判定する文
			//ファイル名が「数字8桁.rcd」なのが条件で判定する
			//条件にあっているものをmatchを使用して探す
			if(fileName.matches("[0-9]{8}[.]rcd")) { //rcdファイルに判別したファイルの中身を追加する。(trueの場合）
				rcdFiles.add(files[i]);
			}
		}
		String fileName = "";
		BufferedReader br = null;
		//rcdFilesに複数の売上ファイルの情報を格納しているので、その数だけ繰り返します。
		for(int i = 0; i < rcdFiles.size(); i++) {


			//files[i].getName() でファイル名が取得できます。
	     	 //String fileName =rcdFiles.get(i).getName();でファイル名を取得できる
			 fileName =rcdFiles.get(i).getName();
		}

			//判別したファイルをファイルreaderに移すそれをbufferに移動して保持
			try {


				File file = new  File (args[0], fileName);
				FileReader fr = new FileReader(file);
				br = new BufferedReader(fr);


				//売り上げリストの名前を新たに宣言する(line,list,code,salse)
				String line =  br.readLine();
				//売上ファイルのList,1行目には支店コード、2行目には売上金額が入っています。カンマなし
				String list  []  = line.split("");
				//Mapに二つの項目をいれるための宣言
				String code = list[0];
				String salse =list[1];
				String.valueOf(salse);

				//売上ファイルから読み込んだ売上金額をMapに加算していくために、型の変換を行います。
				long fileSalse = Long.parseLong(salse);
				//読み込んだ売上金額を加算します。
				//Map(HashMap)から値を取得する
				Long saleAmount = branchSales .get(code) + fileSalse;

				//加算した売上⾦額をMapに追加します。

			} catch(IOException e) {
		      System.out.println(UNKNOWN_ERROR);

	        } finally {
		     // ファイルを開いている場合
	        	if(br != null) {
						// ファイルを閉じる
				   	try {
						br.close();
					} catch (IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
	        	}

		    }
	}


	/**
	 * 支店定義ファイル読み込み処理
	 *
	 * @param フォルダパス
	 * @param ファイル名
	 * @param 支店コードと支店名を保持するMap
	 * @param 支店コードと売上金額を保持するMap
	 * @return 読み込み可否
	 */
		private static boolean readFile(String path, String fileName, Map<String, String> branchNames, Map<String, Long> branchSales) {
			BufferedReader br = null;

			try {
				//ファイルをファイルreaderに移すそれをbufferに移動して保持
				File file = new File(path, fileName);
				FileReader fr = new FileReader(file);
				br = new BufferedReader(fr);

				String line;
				// 一行ずつ読み込む
				while((line = br.readLine()) != null) {
					// ※ここの読み込み処理を変更してください。(処理内容1-2)

					//spritを使って「,」（カンマ）で分割すると、
					//item[0]には支店コード、item[1]には支店名が格納される
					String[] items = line.split(",");

					//Mapに追加する2つの情報をputの引数として指定します。
					//Mapの値の保持
					String code = items[0];
					String name = items[1];
					branchNames.put(code,name);
					branchSales.put(code,(long) 0);
				}


				//エラー
			} catch(IOException e) {
				System.out.println(UNKNOWN_ERROR);
				return false;
			} finally {
				// ファイルを開いている場合
				if(br != null) {
					try {
						// ファイルを閉じる
						br.close();
					} catch(IOException e) {
						System.out.println(UNKNOWN_ERROR);
						return false;
					}

				}
			}
			return true;
		}

	/**
	 * 支店別集計ファイル書き込み処理
	 *
	 * @param フォルダパス
	 * @param ファイル名
	 * @param 支店コードと支店名を保持するMap
	 * @param 支店コードと売上金額を保持するMap
	 * @return 書き込み可否
	 */
	private static boolean writeFile(String path, String fileName, Map<String, String> branchNames, Map<String, Long> branchSales) {
		// ※ここに書き込み処理を作成してください。(処理内容3-1)

		//BufferWriterを作成することを宣言
		//BufferWriterに出力するために、brの内容をFileWriterに書き込んでから移動する
		//FileWriterのためのファイル作成も行う。
		BufferedWriter br = null;



