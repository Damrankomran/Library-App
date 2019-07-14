
import java.sql.*;
import tablolar.IslemKayit;
import tablolar.Yazar;
import tablolar.Kitaplar;
import tablolar.Okurlar;

public class Database {

	
	static Connection baglanti;
	public static Connection baglantiAc() {
	        // SQLite connection string
	        String url = "jdbc:sqlite:C://sqlite/kutuphane.db";
	        
	        try {
	        	baglanti = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            System.out.println("Ba�lant� ba�ar�s�z.");
	        }
	        //System.out.println("Ba�lant� ba�ar�l�.");
	        return baglanti;
	    }
	
	//Veritaban�n� kapatan fonksiyon
	public static void baglantiKapa(){
			try{
				baglanti.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	
	public static void tabloSil(int no, String kayit){
		String sorgu;
		if	   (kayit.equalsIgnoreCase("yazar"))				sorgu = "DELETE FROM yazar WHERE no = ?";
		else if(kayit.equalsIgnoreCase("okur"))					sorgu = "DELETE FROM okurlar WHERE no = ?";
		else if(kayit.equalsIgnoreCase("stok"))					sorgu = "DELETE FROM stok_adet WHERE kitap_no = ?";
		else if(kayit.equalsIgnoreCase("kayit"))				sorgu = "DELETE FROM kayit WHERE islem_no = ?"; 
		else													sorgu = "DELETE FROM kitaplar WHERE no = ?";
		
		if(kayit.equalsIgnoreCase("kayit")){ //Silinen kay�t i�in stok_adet tablosunda ekleme yap�lacak
			int kitapNo = kitapNoBul(no);
			int kitapAdet = stokAdetAl(kitapNo);
			
			stokAdetGuncelle(kitapNo, kitapAdet+1);
		}
		try{
			Connection baglanti  = baglantiAc();
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			
			ps.setInt(1, no);
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();
			System.out.println(kayit+" kayd� ba�ar�yla silinmi�tir.\n");
			
		}
		catch (SQLException se) {
            se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(kayit+" kayd� silme i�lemi ba�ar�s�z olmu�tur!!");
		}
		
	}
	
	
	
	public static void yazarEkle(Yazar yazar){
		String sorgu = "INSERT INTO yazar(no,ad) VALUES (?,?)";
		try{
			Connection baglanti = baglantiAc();
		
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			ps.setInt(1, yazar.getYazarNo());
			ps.setString(2,yazar.getYazarAd());
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();
			System.out.println("Yazar kayd� ba�ar�yla eklenmi�tir.\n");
		}catch (SQLException se) {
			System.out.println("Bu numaraya sahip bir yazar zaten mevcut!!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void yazarListele(){
		String sorgu = "SELECT *FROM yazar ORDER BY no";
		try{
			Connection baglanti = baglantiAc();
			Statement st  		= baglanti.createStatement();
			ResultSet rs  		= st.executeQuery(sorgu);
			
			System.out.println("Yazar No      	Yazar Ad");
			System.out.println("---------------------------------");
			while(rs.next()){
				int no 		= rs.getInt("no");
				String isim = rs.getString("ad");
				System.out.println(no+"	  "+isim);
			}
			st.close();
			rs.close();
			baglantiKapa();
		}catch (SQLException se) {
            se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void yazarGuncelle(Yazar yazar){
		String sorgu = "UPDATE yazar SET ad = ? WHERE no = ?";
		try{
			Connection baglanti  = baglantiAc();
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			
			ps.setString(1, yazar.getYazarAd());
			ps.setInt(2, yazar.getYazarNo());
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();
			System.out.println("Yazar bilgileri g�ncellenmi�tir.\n");
		}catch (SQLException se) {
            System.out.println("Bu numaraya sahip bir yazar zaten mevcut!!");
		}catch(Exception e){
			System.out.println("Yazar g�ncelleme i�lemi ba�ar�s�z olmu�tur.");
			e.printStackTrace();
		}
		
	}

	public static boolean yazarVarYok(int yazarNo){
		String sorgu = "SELECT *FROM yazar WHERE no = "+yazarNo;
		try{
			Connection baglanti = baglantiAc();
			Statement st = baglanti.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			System.out.println("Yazar numaras� kontrol ediliyor..");
			while(rs.next()){
				int no = rs.getInt("no");
				if(no == yazarNo){
					rs.close();
					st.close();
					baglantiKapa();
					System.out.println("Bu numarada kay�tl� bir yazar mevcut.");
					return true; //Gelen yazarNo'ya sahip bir kay�t bulursa true d�necek.
				}
			}
			rs.close();
			st.close();
			baglantiKapa();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("B�yle bir yazar numaras� bulunmuyor.");
		return false;
	}
	
	
	
	
	public static void kitapEkle(Kitaplar kitap){
		String sorgu = "INSERT INTO kitaplar(no,isbn,ad,yazar_no,yayinevi) VALUES (?,?,?,?,?)";
		try{
			Connection baglanti  = baglantiAc();
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			
			ps.setInt(1, kitap.getNo());
			ps.setString(2,kitap.getIsbn());
			ps.setString(3,kitap.getAd());
			ps.setInt(4,kitap.getYazarNo());
			ps.setString(5, kitap.getYayinevi());
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();
			System.out.println("Kitap kayd� ba�ar�yla olu�turulmu�tur.\n");
			}
			catch (SQLException se) {
	            System.out.println("Bu kitap numaras�na ait bir kitap zaten mevcut!!\n");
	        }
			catch(Exception e){
			e.printStackTrace();
			System.out.println("Kitap kayd� ba�ar�s�z olmu�tur.");
		}
	}

	public static void kitapListele(){
		String sorgu = "SELECT  *FROM kitaplar ORDER BY no";
		try{
			Connection baglanti  = baglantiAc();
			Statement st		 = baglanti.createStatement();
			ResultSet rs 		 = st.executeQuery(sorgu);
			
			System.out.println("No  ISBN	AD						Yazar No    Yayinevi");
			System.out.println("-------------------------------------------------------------------------------------");
			
			while(rs.next()){
				String zeropad 	= "...................................................";
				int no 			= rs.getInt("no");
				String isbn 	= rs.getString("isbn");
				String ad 		= rs.getString("ad");
				int yazarNo 	= rs.getInt("yazar_no");
				String yayinevi = rs.getString("yayinevi");
				System.out.println(no+"  "+isbn+"  "+(ad+zeropad.substring(ad.length()))+"  "+yazarNo+"	   "+yayinevi);
			}
			st.close();
			rs.close();
			baglantiKapa();
		}catch (SQLException se) {
            se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void kitapGuncelle(Kitaplar kitap){
		String sorgu = "UPDATE kitaplar SET isbn = ?, ad = ?, yazar_no = ?, yayinevi = ? WHERE no = ?";
		try{
			Connection baglanti  = baglantiAc();
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			
			ps.setString(1, kitap.getIsbn());
			ps.setString(2, kitap.getAd());
			ps.setInt(3, kitap.getYazarNo());
			ps.setString(4, kitap.getYayinevi());
			ps.setInt(5, kitap.getNo());
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();
			System.out.println("Kitap bilgileri g�ncellenmi�tir.\n");
		}catch (SQLException se) {
			System.out.println("Bu kitap numaras�na ait bir kitap zaten mevcut!!\n");
		}catch(Exception e){
			System.out.println("Kitap g�ncelleme i�lemi ba�ar�s�z olmu�tur.");
			e.printStackTrace();
		}
	}
	
	public static boolean kitapVarYok(int kitapNo){
		String sorgu = "SELECT *FROM kitaplar WHERE no = "+kitapNo;
		try{
			Connection baglanti = baglantiAc();
			Statement st = baglanti.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			
			while(rs.next()){
				int kitap_no = rs.getInt("no");
				if(kitap_no == kitapNo){
					System.out.println("Bu numaraya sahip bir kitap kay�tlarda mevcut!!\n");
					rs.close();
					st.close();
					baglantiKapa();
					return true;
					
				}
			}
			rs.close();
			st.close();
			baglantiKapa();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Bu numaraya sahip kitap yok.");
		return false;
	}
	
	public static boolean isbnVarYok(String Isbn){
		String sorgu = "SELECT *FROM kitaplar WHERE isbn = "+Isbn;
		try{
			Connection baglanti = baglantiAc();
			Statement st = baglanti.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			
			while(rs.next()){
				String isbn = rs.getString("isbn");
				if(isbn.equalsIgnoreCase(Isbn)) {
					rs.close();
					st.close();
					baglantiKapa();
					System.out.println("B�yle bir ISBN mevcutt!!\n");
					return false;
				}
			}
			
			rs.close();
			st.close();
			baglantiKapa();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	

	public static void okurlarEkle(Okurlar okurlar){
		String sorgu = "INSERT INTO okurlar(no, ad,soyad,bolum) VALUES(?,?,?,?)";
		try{
			Connection baglanti  = baglantiAc();
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			
			ps.setInt(1, okurlar.getOgrNo());
			ps.setString(2, okurlar.getOgrIs�m());
			ps.setString(3, okurlar.getOgrSoyisim());
			ps.setString(4, okurlar.getBolum());
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();
			System.out.println("Ogrenci kaydi ba�ar�yla eklenmi�tir.\n");
		}catch (SQLException se) {
            System.out.println("Bu numaraya sahip bir ��renci kayd� mevcut!!\n");
		}catch(Exception e){
			System.out.println("Ogrenci kaydi ba�ar�s�z olmu�tur.");
			e.printStackTrace(); 
		}
		
	}
	
	public static void okurlarListele(){
		String sorgu = "SELECT *FROM okurlar ORDER BY no";
		
		try{
			Connection baglanti = baglantiAc();
			Statement st 		= baglanti.createStatement();
			ResultSet rs 		= st.executeQuery(sorgu);
			System.out.println("No 	Ad			Soyad		Bolum");
			System.out.println("-------------------------------------------------------------");
			while(rs.next()){
				String zeropad 	= "...............";
				int no 		 = rs.getInt("no");
				String ad	 = rs.getString("ad");
				String soyad = rs.getString("soyad");
				String bolum = rs.getString("bolum");
				System.out.println(no+"	"+(ad+zeropad.substring(ad.length()))+"	"+(soyad+zeropad.substring(soyad.length()))+"  "+bolum);
				// 12345678
				// 		
			}
			st.close();
			rs.close();
			baglantiKapa();
		}catch (SQLException se) {
            se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void okurlarG�ncelle(Okurlar okur){
		String sorgu = "UPDATE okurlar SET ad = ?, soyad = ?, bolum = ? WHERE no=?";
		try{
			Connection baglanti = baglantiAc();
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			
			ps.setString(1, okur.getOgrIs�m());
			ps.setString(2, okur.getOgrSoyisim());
			ps.setString(3, okur.getBolum());
			ps.setInt(4, okur.getOgrNo());
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();
			System.out.println("Okur bilgileri g�ncellenmi�tir.\n");
		}catch (SQLException se) {
			System.out.println("Bu numaraya sahip bir ��renci kayd� mevcut!!\n");
		}catch(Exception e){
			System.out.println("Okur bilgileri g�ncellemesi ba�ar�s�z oldu!!");
			e.printStackTrace();
		}
		
	}
	
	public static boolean okurVarYok(int okurNo){
		String sorgu = "SELECT *FROM okurlar WHERE no = "+okurNo;
		try{
			Connection baglanti = baglantiAc();
			Statement st = baglanti.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			
			while(rs.next()){
				int no = rs.getInt("no");
				if(no == okurNo){
					rs.close();
					st.close();
					baglantiKapa();
					System.out.println("Bu okur kay�tlarda mevcut \n");
					return true;
				}
			}
			rs.close();
			st.close();
			baglantiKapa();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("B�yle bir okur mevcut de�il!\n");
		return false;
	}
	

	//�lk kitap giri�inde bu fonksiyon �a�r�l�r.
	public static void stokEkle(int kitapNo, int adet){
		String sorgu = "INSERT INTO stok_adet(kitap_no,kitap_adet) VALUES(?,?)";
		try{
			Connection baglanti  = baglantiAc();
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			ps.setInt(1, kitapNo);
			ps.setInt(2, adet);
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();
			System.out.println("Kitap Stok_Adet tablosuna ba�ar�yla eklenmi�tir.\n");
		}catch (SQLException se) {
            se.printStackTrace();
		}catch(Exception e){
			System.out.println("Kitap Stok_Adet tablosuna eklenemedi!!!");
			e.printStackTrace();
		}
	}
	
	public static void stokListele(){
		String sorgu = "SELECT s.kitap_no, k.ad, s.kitap_adet FROM stok_adet s, kitaplar k WHERE s.kitap_no = k.no ORDER BY kitap_no";
		try{
			Connection baglanti = baglantiAc();
			Statement st 		= baglanti.createStatement();
			ResultSet rs 		= st.executeQuery(sorgu);
			
			while(rs.next()){
				String zeropad 	= "...................................................";
				int kitap_no = rs.getInt("kitap_no");
				String kitap_isim = rs.getString("ad");
				int adet 	 = rs.getInt("kitap_adet");
				System.out.println(kitap_no+"  "+(kitap_isim+zeropad.substring(kitap_isim.length()))+" "+adet);
			}
			rs.close();
			st.close();
			baglantiKapa();
		}catch (SQLException se) {
            se.printStackTrace();
		}catch(Exception e){
			System.out.println("Stok kayd� g�r�nt�lenemiyor!!");
			e.printStackTrace();
		}
	}
	
	public static void stokAdetGuncelle(int kitapNo, int adet){
		String sorgu = "UPDATE stok_adet SET kitap_adet = ? WHERE kitap_no = ? ";
		try{
			Connection baglanti  = baglantiAc();
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			
			ps.setInt(1, adet);
			ps.setInt(2, kitapNo);
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();
			System.out.println("Stok kayd� ba�ar�yla g�ncellenmi�tir.\n");
		}catch (SQLException se) {
            se.printStackTrace();
		}catch(Exception e){
			System.out.println("Stok kayd� g�ncellemesi ba�ar�s�z oldu!!");
			e.printStackTrace();
		}
		
	}

	public static int stokAdetAl(int kitapNo){
		String sorgu = "SELECT *FROM stok_adet WHERE kitap_no ="+kitapNo;
		
		try{
			Connection baglanti = baglantiAc();
			Statement st = baglanti.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			while(rs.next()){
				 int adet = rs.getInt("kitap_adet");
				 rs.close();
				 st.close();
				 baglantiKapa();
				 return adet;
			}
			 rs.close();
			 st.close();
			 baglantiKapa();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static int kitapNoBul(int islemNo){
		String sorgu = "SELECT *FROM kayit WHERE islem_no ="+islemNo;
		try{
			Connection baglanti = baglantiAc();
			Statement st = baglanti.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			
			while(rs.next()){
				int kitapNo = rs.getInt("kitap_no");
				st.close();
				rs.close();
				baglantiKapa();
				return kitapNo;
			}
			st.close();
			rs.close();
			baglantiKapa();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
	

	
	public static void kayitEkle(IslemKayit kayit){
		String sorgu = "INSERT INTO kayit(islem_no,alis_tarih,iade_tarih,okur_no,kitap_no) VALUES(?,?,?,?,?) ";
		
		try{
			Connection baglanti  = baglantiAc();
			PreparedStatement ps = baglanti.prepareStatement(sorgu);
			
			ps.setInt(1, kayit.getIslemNo());
			ps.setString(2, kayit.getAlimTarih());
			ps.setString(3, kayit.getIadeTarih());
			ps.setInt(4, kayit.getOgrNo());
			ps.setInt(5, kayit.getKitapNo());
			
			int kitapAdet = stokAdetAl(kayit.getKitapNo());
			stokAdetGuncelle(kayit.getKitapNo(), kitapAdet-1);
			
			ps.executeUpdate();
			ps.close();
			baglantiKapa();

			
			
			System.out.println("�slem kay�t tablosuna ba�ar�yla eklenmi�tir.\n");
			
		}catch (SQLException se) {
            System.out.println("Bu i�lem numaras�na sahip kay�t mevcut!!\n");
		}catch(Exception e){
			System.out.println("��lem kay�t� olu�turulurken hata!!");
			e.printStackTrace();
		}
		
	}
	
	public static void kayitListele(){
		String sorgu = "SELECT *FROM kayit ORDER BY islem_no";
		try{
			Connection baglanti = baglantiAc();
			Statement st = baglanti.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			
			System.out.println("��lem No  	Al�� Tarihi  	    �ade Tarihi   Okur No    Kitap No");
			System.out.println("-----------------------------------------------------------------------");
			while(rs.next()){
				int islemNo = rs.getInt("islem_no");
				String alimTarih = rs.getString("alis_tarih");
				String iadeTarih = rs.getString("iade_tarih");
				int okurNo = rs.getInt("okur_no");
				int kitapNo = rs.getInt("kitap_no");
				
				System.out.println(islemNo+" 		"+alimTarih+" 	    "+iadeTarih+" 	     "+okurNo+" 	         "+kitapNo);
			}
			st.close();
			rs.close();
			baglantiKapa();
		}catch (SQLException se) {
            se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static boolean kayitVarYok(int islemNo){
		String sorgu = "SELECT *FROM kayit WHERE islem_no = "+islemNo;
		try{
			Connection baglanti = baglantiAc();
			Statement st = baglanti.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			
			while(rs.next()){
				int islem = rs.getInt("islem_no");
				if(islem == islemNo){
					st.close();
					rs.close();
					baglantiKapa();
					System.out.println("Bu i�lem numaras� i�lem-kay�t tablosunda mevcut. \n");
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return false;
	}
	
	
	
	
	
	
	
	
	
}
