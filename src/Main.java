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
			System.out.println("KÜTÜPHANE VERÝTABANINA HOÞGELDÝNÝZ");
			
			System.out.println("1)Yazar Ýþlemleri\n2)Kitap Ýþlemleri\n3)Okur Ýþlemleri\n4)Ýþlem Kayýt Oluþturma\n0)Çýkýþ");
			System.out.println("Ýþlemi seciniz:");
			menu = in.nextInt();
			
			int islem = 10;
			
			if(menu == 1){
				
				while(islem != 0){
				System.out.println("Yazar Ýþlemleri Menüsüne Hoþgeldiniz\n");
				Database.yazarListele();
				System.out.println("\n");
				System.out.println("1)Yazar Ekleme | 2)Yazar Güncelleme | 3)Yazar Silme | 0)Geri");
				System.out.println("Ýþlemi seçiniz: ");
				islem = in.nextInt();
				
					if(islem==1){
						System.out.println("Yazar Ekleme Ýþlemi Baþlatýlýyor..");
						Yazar yazar = new Yazar();
						
						do{
							System.out.println("Yazarýn numarasýný giriniz:");
							yazar.setYazarNo(in.nextInt());
						}while(Database.yazarVarYok(yazar.getYazarNo())==true);
						
						System.out.println("Yazarýn adýný giriniz:");
						yazar.setYazarAd(in.next());
						
						Database.yazarEkle(yazar);
						
						in.nextLine();
					}
					else if(islem == 2){
						Yazar yazar = new Yazar();
						System.out.println("Yazar Güncelleme Ýþlemi Baþlatýlýyor..");
						do{
							System.out.println("Güncellenecek yazarýn numarasýný giriniz:");
							yazar.setYazarNo(in.nextInt());
						}while(Database.yazarVarYok(yazar.getYazarNo())==false);
						System.out.println("Yazarýn yeni adýný giriniz:");
						yazar.setYazarAd(in.next());
						
						Database.yazarGuncelle(yazar);
					}
					else if(islem == 3){
						System.out.println("Yazar Silme Ýþlemi Baþlatýlýyor..");
						
						System.out.println("Silinecek olan yazarýn numarasýný giriniz:");
						Database.tabloSil(in.nextInt(), "yazar");
					}
				
				}
					
			}
			
			
			if(menu == 2){
				while(islem!=0){
					System.out.println("Kitap Ýþlemleri Menüsüne Hoþgeldiniz");
					
					Database.kitapListele();
					System.out.println("\n");
					
					System.out.println("1)Kitap Ekle | 2)Kitap Güncelle | 3)Kitap Sil | 0)Geri");
					System.out.println("Ýþlemi seçiniz: ");
					islem = in.nextInt();
					
					if(islem==1){
						Kitaplar kitap = new Kitaplar();
						
						System.out.println("Kitap Ekleme Ýþlemi Baþlatýlýyor..");
						
						do{
							System.out.println("Kitabýn numarasýný giriniz:");
							kitap.setNo(in.nextInt());
						}while(Database.kitapVarYok(kitap.getNo())==true);
						
						do{
							System.out.println("Kitabýn ISBN numarasýný giriniz:");
							kitap.setIsbn(in.next());
						}while(Database.isbnVarYok(kitap.getIsbn())==false);
						
						System.out.println("Kitabýn adýný giriniz:");
						kitap.setAd(in.next());
						
						Database.yazarListele(); //Yazar numarasý girilmesi için yazarlarý numaralarý ile birlikte listeledik
						System.out.println("\n");
						
						do{
							System.out.println("Kitabýn yazar numarasýný giriniz:");
							kitap.setYazarNo(in.nextInt());
						}while(Database.yazarVarYok(kitap.getYazarNo())==false);
						
						System.out.println("Kitabýn yayinevini giriniz:");
						kitap.setYayinevi(in.next());
						
						//Yeni girilen kitabý belirtilen adet miktarýný stok_adet tablosuna kaydettik
						System.out.println("Kitap adetini giriniz:");
						Database.stokEkle(kitap.getNo(), in.nextInt());
						
						Database.kitapEkle(kitap);
						in.nextLine();
					}
					
					if(islem==2){
						Kitaplar kitap = new Kitaplar();
						
						System.out.println("Kitap Güncelleme Ýþlemi Baþlatýlýyor..");
						do{
							System.out.println("Güncellenecek olan kitabýn no'sunu giriniz:");
							kitap.setNo(in.nextInt());
						}while(Database.kitapVarYok(kitap.getNo())==false);
						
						do{
							System.out.println("Kitabýn yeni ISBN numarasýný girin:");
							kitap.setIsbn(in.next());
						}while(Database.isbnVarYok(kitap.getIsbn())==false);
						
						System.out.println("Kitabýn yeni adýný giriniz:");
						kitap.setAd(in.next());
						
						Database.yazarListele();
						System.out.println("\n");
						
						do{
							System.out.println("Kitabýn yeni yazar numarasýný giriniz:");
							kitap.setYazarNo(in.nextInt());
						}while(Database.yazarVarYok(kitap.getYazarNo())==false);
						
						System.out.println("Kitabýn yeni yayýnevini yazýnýz:");
						kitap.setYayinevi(in.next());
						
						Database.kitapGuncelle(kitap);
					}
					
					if(islem == 3){
						System.out.println("Kitap Silme Ýþlemi Baþlatýlýyor..");
						int kitapNo;
						
						System.out.println("Silinecek olan kitabýn numarasýný giriniz:");
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
					System.out.println("Okur Ýþlemleri Menüsüne Hoþgeldiniz\n");
					Database.okurlarListele();
					System.out.println("\n");
					System.out.println("1)Okur Ekleme | 2)Okur Güncelleme | 3)Okur Silme | 0)Geri");
					System.out.println("Ýþlemi seçiniz: ");
					islem = in.nextInt();
					
					if(islem==1){// Ekleme iþlemi
						Okurlar okur = new Okurlar();
						System.out.println("Okur Ekleme Ýþlemi Baþlatýlýyor..");
						
						do{
							System.out.println("Okurun numarasýný giriniz:");
							okur.setOgrNo(in.nextInt());
						}while(Database.okurVarYok(okur.getOgrNo())==true);
						
						System.out.println("Okurun adýný giriniz:");
						okur.setOgrIsým(in.next());
						
						System.out.println("Okurun soyadýný giriniz:");
						okur.setOgrSoyisim(in.next());
						
						System.out.println("Okurun bölümü giriniz:");
						okur.setBolum(in.next());
						
						Database.okurlarEkle(okur);
						in.nextLine();
					}
					if(islem == 2 ){ //güncelleme iþlemi
						Okurlar okur = new Okurlar();
						System.out.println("Okur Güncelleme Ýþlemine Baþlatýlýyor..");
						do{
							System.out.println("Güncellenecek olan okurun numarasýný giriniz:");
							okur.setOgrNo(in.nextInt());
						}while(Database.okurVarYok(okur.getOgrNo())==false);
						
						System.out.println("Okurun yeni adýný giriniz:");
						okur.setOgrIsým(in.next());
						
						System.out.println("Okurun yeni soyadýný giriniz:");
						okur.setOgrSoyisim(in.next());
						
						System.out.println("Okurun yeni bölümünü giriniz:");
						okur.setBolum(in.next());
						
						Database.okurlarGüncelle(okur);
					}
					if(islem == 3){ // Silme iþlemi
						int okurNo;
						System.out.println("Okur Silme Ýþlemi Baþlatýlýyor..");
						
						System.out.println("Silinecek olan okurun numarasýný giriniz:");
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
				System.out.println("Ýþlem Kaydý Oluþturma Menüsüne Hoþgeldiniz");
				Database.kayitListele();
				System.out.println("\n");
				System.out.println("1) Kayýt Ekleme | 2)Kayýt Silme | 0)Geri");
				islem = in.nextInt();
					
					if(islem==1){
						IslemKayit kayit = new IslemKayit();
						System.out.println("\n");
						
						System.out.println("Kayýt Ekleme Ýþlemi Baþlatýlýyor.");
						do{
							System.out.println("Ýþlem numarasýný giriniz:");
							kayit.setIslemNo(in.nextInt());
						}while(Database.kayitVarYok(kayit.getIslemNo())==true);
						
						System.out.println("Alým tarihini giriniz: ");
						kayit.setAlimTarih(in.next());
						
						System.out.println("Ýade tarihini giriniz: ");
						kayit.setIadeTarih(in.next());
						
						Database.okurlarListele();
						System.out.println("\n");
						
						do{
							System.out.println("Okur numarasýný giriniz: ");
							kayit.setOgrNo(in.nextInt());
						}while(Database.okurVarYok(kayit.getOgrNo())==false);
						
						Database.stokListele();
						System.out.println("\n");
						
						do{
							System.out.println("Kitap numarasýný giriniz: ");
							kayit.setKitapNo(in.nextInt());
							if(Database.stokAdetAl(kayit.getKitapNo())==0)
								System.out.println("Kitap adedi yetersiz!!");
						}while(Database.stokAdetAl(kayit.getKitapNo())==0);
						
						Database.kayitEkle(kayit);
						in.nextLine();
					}
					if(islem == 2){
						int islemNo;
						System.out.println("Kayýt Silme Ýþlemi Baþlatýlýyor. ");
						
						System.out.println("Silinecek olan kaydýn islem numarasýný giriniz:");
						islemNo = in.nextInt();
						
						Database.tabloSil(islemNo,"kayit");
					}
			
				}
				
			}
			
			
			
		}
	
	
	
	
	
	}
	

}
