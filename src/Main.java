import tablolar.IslemKayit;
import tablolar.Yazar;
import tablolar.Kitaplar;
import tablolar.Okurlar;
import java.util.Scanner;

public class Main {

	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		int menu=10;
		
		while(menu != 0){
			System.out.println("K�T�PHANE VER�TABANINA HO�GELD�N�Z");
			
			System.out.println("1)Yazar ��lemleri\n2)Kitap ��lemleri\n3)Okur ��lemleri\n4)��lem Kay�t Olu�turma\n0)��k��");
			System.out.println("��lemi seciniz:");
			menu = in.nextInt();
			
			int islem = 10;
			
			if(menu == 1){
				
				while(islem != 0){
				System.out.println("Yazar ��lemleri Men�s�ne Ho�geldiniz\n");
				Database.yazarListele();
				System.out.println("\n");
				System.out.println("1)Yazar Ekleme | 2)Yazar G�ncelleme | 3)Yazar Silme | 0)Geri");
				System.out.println("��lemi se�iniz: ");
				islem = in.nextInt();
				
					if(islem==1){
						System.out.println("Yazar Ekleme ��lemi Ba�lat�l�yor..");
						Yazar yazar = new Yazar();
						
						do{
							System.out.println("Yazar�n numaras�n� giriniz:");
							yazar.setYazarNo(in.nextInt());
						}while(Database.yazarVarYok(yazar.getYazarNo())==true);
						
						System.out.println("Yazar�n ad�n� giriniz:");
						yazar.setYazarAd(in.next());
						
						Database.yazarEkle(yazar);
						
						in.nextLine();
					}
					else if(islem == 2){
						Yazar yazar = new Yazar();
						System.out.println("Yazar G�ncelleme ��lemi Ba�lat�l�yor..");
						do{
							System.out.println("G�ncellenecek yazar�n numaras�n� giriniz:");
							yazar.setYazarNo(in.nextInt());
						}while(Database.yazarVarYok(yazar.getYazarNo())==false);
						System.out.println("Yazar�n yeni ad�n� giriniz:");
						yazar.setYazarAd(in.next());
						
						Database.yazarGuncelle(yazar);
					}
					else if(islem == 3){
						System.out.println("Yazar Silme ��lemi Ba�lat�l�yor..");
						
						System.out.println("Silinecek olan yazar�n numaras�n� giriniz:");
						Database.tabloSil(in.nextInt(), "yazar");
					}
				
				}
					
			}
			
			
			if(menu == 2){
				while(islem!=0){
					System.out.println("Kitap ��lemleri Men�s�ne Ho�geldiniz");
					
					Database.kitapListele();
					System.out.println("\n");
					
					System.out.println("1)Kitap Ekle | 2)Kitap G�ncelle | 3)Kitap Sil | 0)Geri");
					System.out.println("��lemi se�iniz: ");
					islem = in.nextInt();
					
					if(islem==1){
						Kitaplar kitap = new Kitaplar();
						
						System.out.println("Kitap Ekleme ��lemi Ba�lat�l�yor..");
						
						do{
							System.out.println("Kitab�n numaras�n� giriniz:");
							kitap.setNo(in.nextInt());
						}while(Database.kitapVarYok(kitap.getNo())==true);
						
						do{
							System.out.println("Kitab�n ISBN numaras�n� giriniz:");
							kitap.setIsbn(in.next());
						}while(Database.isbnVarYok(kitap.getIsbn())==false);
						
						System.out.println("Kitab�n ad�n� giriniz:");
						kitap.setAd(in.next());
						
						Database.yazarListele(); //Yazar numaras� girilmesi i�in yazarlar� numaralar� ile birlikte listeledik
						System.out.println("\n");
						
						do{
							System.out.println("Kitab�n yazar numaras�n� giriniz:");
							kitap.setYazarNo(in.nextInt());
						}while(Database.yazarVarYok(kitap.getYazarNo())==false);
						
						System.out.println("Kitab�n yayinevini giriniz:");
						kitap.setYayinevi(in.next());
						
						//Yeni girilen kitab� belirtilen adet miktar�n� stok_adet tablosuna kaydettik
						System.out.println("Kitap adetini giriniz:");
						Database.stokEkle(kitap.getNo(), in.nextInt());
						
						Database.kitapEkle(kitap);
						in.nextLine();
					}
					
					if(islem==2){
						Kitaplar kitap = new Kitaplar();
						
						System.out.println("Kitap G�ncelleme ��lemi Ba�lat�l�yor..");
						do{
							System.out.println("G�ncellenecek olan kitab�n no'sunu giriniz:");
							kitap.setNo(in.nextInt());
						}while(Database.kitapVarYok(kitap.getNo())==false);
						
						do{
							System.out.println("Kitab�n yeni ISBN numaras�n� girin:");
							kitap.setIsbn(in.next());
						}while(Database.isbnVarYok(kitap.getIsbn())==false);
						
						System.out.println("Kitab�n yeni ad�n� giriniz:");
						kitap.setAd(in.next());
						
						Database.yazarListele();
						System.out.println("\n");
						
						do{
							System.out.println("Kitab�n yeni yazar numaras�n� giriniz:");
							kitap.setYazarNo(in.nextInt());
						}while(Database.yazarVarYok(kitap.getYazarNo())==false);
						
						System.out.println("Kitab�n yeni yay�nevini yaz�n�z:");
						kitap.setYayinevi(in.next());
						
						Database.kitapGuncelle(kitap);
					}
					
					if(islem == 3){
						System.out.println("Kitap Silme ��lemi Ba�lat�l�yor..");
						int kitapNo;
						
						System.out.println("Silinecek olan kitab�n numaras�n� giriniz:");
						kitapNo = in.nextInt();
						if(Database.kitapVarYok(kitapNo)==false){
							System.out.println("\n");
						}
						else{
							Database.tabloSil(kitapNo, "kitap");
							Database.tabloSil(kitapNo,"stok");
						}
					}
					
					
				}
				
				
			}
			if(menu == 3){
				while(islem != 0){
					System.out.println("Okur ��lemleri Men�s�ne Ho�geldiniz\n");
					Database.okurlarListele();
					System.out.println("\n");
					System.out.println("1)Okur Ekleme | 2)Okur G�ncelleme | 3)Okur Silme | 0)Geri");
					System.out.println("��lemi se�iniz: ");
					islem = in.nextInt();
					
					if(islem==1){// Ekleme i�lemi
						Okurlar okur = new Okurlar();
						System.out.println("Okur Ekleme ��lemi Ba�lat�l�yor..");
						
						do{
							System.out.println("Okurun numaras�n� giriniz:");
							okur.setOgrNo(in.nextInt());
						}while(Database.okurVarYok(okur.getOgrNo())==true);
						
						System.out.println("Okurun ad�n� giriniz:");
						okur.setOgrIs�m(in.next());
						
						System.out.println("Okurun soyad�n� giriniz:");
						okur.setOgrSoyisim(in.next());
						
						System.out.println("Okurun b�l�m� giriniz:");
						okur.setBolum(in.next());
						
						Database.okurlarEkle(okur);
						in.nextLine();
					}
					if(islem == 2 ){ //g�ncelleme i�lemi
						Okurlar okur = new Okurlar();
						System.out.println("Okur G�ncelleme ��lemine Ba�lat�l�yor..");
						do{
							System.out.println("G�ncellenecek olan okurun numaras�n� giriniz:");
							okur.setOgrNo(in.nextInt());
						}while(Database.okurVarYok(okur.getOgrNo())==false);
						
						System.out.println("Okurun yeni ad�n� giriniz:");
						okur.setOgrIs�m(in.next());
						
						System.out.println("Okurun yeni soyad�n� giriniz:");
						okur.setOgrSoyisim(in.next());
						
						System.out.println("Okurun yeni b�l�m�n� giriniz:");
						okur.setBolum(in.next());
						
						Database.okurlarG�ncelle(okur);
					}
					if(islem == 3){ // Silme i�lemi
						int okurNo;
						System.out.println("Okur Silme ��lemi Ba�lat�l�yor..");
						
						System.out.println("Silinecek olan okurun numaras�n� giriniz:");
						okurNo = in.nextInt();
						
						if(Database.okurVarYok(okurNo)==false)
							System.out.println("\n");
						else
							Database.tabloSil(okurNo, "okur");
						
					}
					
				}
				
			}
			if(menu == 4){
				while(islem!=0){
				System.out.println("��lem Kayd� Olu�turma Men�s�ne Ho�geldiniz");
				Database.kayitListele();
				System.out.println("\n");
				System.out.println("1) Kay�t Ekleme | 2)Kay�t Silme | 0)Geri");
				islem = in.nextInt();
					
					if(islem==1){
						IslemKayit kayit = new IslemKayit();
						System.out.println("\n");
						
						System.out.println("Kay�t Ekleme ��lemi Ba�lat�l�yor.");
						do{
							System.out.println("��lem numaras�n� giriniz:");
							kayit.setIslemNo(in.nextInt());
						}while(Database.kayitVarYok(kayit.getIslemNo())==true);
						
						System.out.println("Al�m tarihini giriniz: ");
						kayit.setAlimTarih(in.next());
						
						System.out.println("�ade tarihini giriniz: ");
						kayit.setIadeTarih(in.next());
						
						Database.okurlarListele();
						System.out.println("\n");
						
						do{
							System.out.println("Okur numaras�n� giriniz: ");
							kayit.setOgrNo(in.nextInt());
						}while(Database.okurVarYok(kayit.getOgrNo())==false);
						
						Database.stokListele();
						System.out.println("\n");
						
						do{
							System.out.println("Kitap numaras�n� giriniz: ");
							kayit.setKitapNo(in.nextInt());
							if(Database.stokAdetAl(kayit.getKitapNo())==0)
								System.out.println("Kitap adedi yetersiz!!");
						}while(Database.stokAdetAl(kayit.getKitapNo())==0);
						
						Database.kayitEkle(kayit);
						in.nextLine();
					}
					if(islem == 2){
						int islemNo;
						System.out.println("Kay�t Silme ��lemi Ba�lat�l�yor. ");
						
						System.out.println("Silinecek olan kayd�n islem numaras�n� giriniz:");
						islemNo = in.nextInt();
						
						Database.tabloSil(islemNo,"kayit");
					}
			
				}
				
			}
			
			
			
		}
	
	
	
	
	
	}
	

}
