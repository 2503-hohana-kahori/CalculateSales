package jp.alhinc.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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


//		String ファイルのパス = null;
//		// ※ここから集計処理を作成してください。(処理内容2-1、2-2)
//		//listFilesを使用してfilesという配列に、
//		//指定したパスに存在する全てのファイル(または、ディレクトリ)の情報を格納します。
//		File[] files = new File(ファイルのパス).listFiles();
//
//
//		//filesの数だけ繰り返すことで、
//		//指定したパスに存在する全てのファイル(または、ディレクトリ)の数だけ繰り返されます。
//		for(int i = 0; i < files.length ; i++) {
//			//files[i].getName() でファイル名が取得できます。
//		}
//
//			//matches を使用してファイル名が「数字8桁.rcd」なのか判定します。
//			String ファイル名 = null;
//			String 正規表現構文 = null;
//			if(ファイル名.matches(正規表現構文)) {
//			    //trueの場合の処理
//			}else {
//
//			}
//
//            //List(ArrayList)に値を保持する
//			File[] files1 = new File(ファイルのパス).listFiles();
//           //先にファイルの情報を格納する List(ArrayList) を宣言します。
//		    List<File> rcdFiles = new ArrayList<>();
//
//		    for(int i1= 0; i1 < files1.length ; i1++) {
//			   if(ファイル名.matches(正規表現構文)) {
//		            //売上ファイルの条件に当てはまったものだけ、List(ArrayList) に追加します。
//				rcdFiles.add(files1[i1]);
//			   }
//		    }
//
//		  //rcdFilesに複数の売上ファイルの情報を格納しているので、その数だけ繰り返します。
//		    for(int i = 0; i < rcdFiles.size(); i++) {
//
//		    	String 売上金額 = null;
//				String 支店コード= null;
//		    	String line;
//		    	BufferedReader br = null;
//		    	while((line =br.readLine()) != null) {
//
//		    	//売上ファイルの1行目には支店コード、2行目には売上金額が入っています。
//
//		    	//売上ファイルから読み込んだ売上金額をMapに加算していくために、型の変換を行います。
//		    	long fileSale = Long.parseLong(売上金額);
//
//		    	//読み込んだ売上金額を加算します。
//		    	int longに変換した売上金額;
//
//		    	//Longとして扱うため、Mapに追加するためには型を変換
//		    	long fileSale1 = Long.parseLong(売上金額);
//
//				Map<String, String> 売上金額を入れたMap;
//				String saleAmount = 売上金額を入れたMap.get(支店コード) +  longに変換した売上金額;
//
//		    	//加算した売上金額をMapに追加します。
//				Long  saleAmount1= 売上金額を入れたMap.get(支店コード) + long に変換した売上金額;
//		    	}
//




		    // 支店別集計ファイル書き込み処理
		    if(!writeFile(args[0], FILE_NAME_BRANCH_OUT, branchNames, branchSales)) {
		    	return;
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
				File file = new File(path, fileName);
				FileReader fr = new FileReader(file);
				br = new BufferedReader(fr);

				String line;
				// 一行ずつ読み込む
				while((line = br.readLine()) != null) {
					// ※ここの読み込み処理を変更してください。(処理内容1-2)

					//spritを使って「,」（カンマ）で分割すると、
					//item[0]には支店コード、item[1]には支店名が格納される
					@SuppressWarnings("unused")
					String[]item=line.split(",");
				}
				//Mapに追加する2つの情報をputの引数として指定します。
				//Mapの値の保持
				String 支店コード = null;
				String 支店名 = null;
				branchNames.put(支店コード,支店名);
				branchSales.put(支店コード,(long) 0);

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
//				//ファイルの存在を確認する方法
//				File file = new File(ファイルのパス, ファイルの名前);
//				if(!file.exists()) {
//					//⽀店定義ファイルが存在しない場合、コンソールにエラーメッセージを表⽰します。
//				}
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

//		try {
//			//書き込む処理
//			file.close();
//
//			for (String key : 支店コードを入れたMap.keySet()) {
//				//keyという変数には、Mapから取得したキーが代入されています。
//				//拡張for文で繰り返されているので、1つ⽬のキーが取得できたら、
//				//2つ目の取得...といったように、次々とkeyという変数に上書きされていきます。
//			}
//
//			//改行方法
//			bw.newLine();
//
//
//		} catch(IOException e) {
//			System.out.println(UNKNOWN_ERROR);
//			return false;
//		}finally {
//			// ファイルを開いている場合
//			if(file != null) {
//				try {
//					// ファイルを閉じる
//					file.close();
//				} catch(IOException e) {
//					System.out.println(UNKNOWN_ERROR);
//					return false;
//				}
//				return true;
//			}
//		}
		return true;
	}
}
