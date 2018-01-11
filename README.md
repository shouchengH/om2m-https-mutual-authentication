# om2m-https-mutual-authentication

1. create_self-signed-GSCL.py : 利用Python來創造GSCL的Keystore，存放在PKI資料夾下<br>
2. create_self-signed-NSCL.py : 利用Python來創造NSCL的Keystore ，存放在PKI資料夾下<br>
3. start_GSCL.bat : 開啟GSCL的start.bat檔，內容為C:\Users\cheng\git\org.eclipse.om2m\org.eclipse.om2m.site.gscl\target\products\gscl\win32\win32\x86\，因為是絕對路徑，會需要修改路徑<br>
4. start_NSCL.bat : 開啟NSCL的start.bat檔，內容為C:\Users\cheng\git\org.eclipse.om2m\org.eclipse.om2m.site.nscl\target\products\nscl\win32\win32\x86\，因為是絕對路徑，會需要修改路徑<br>
5. postman : postman存放憑證的地方，以postman對om2m做測試<br>
6. org.eclipse.om2m : om2m的主程式，主要修改org.eclipse.om2m.comm.http Plugin<br>

<br><br>
## 安裝方式
$ git clone https://github.com/shouchengH/om2m-https-mutual-authentication<br>
$ cd om2m-https-mutual-authentication\org.eclipse.om2m<br>
$ mvn install<br>

<br><br>
## 已完成
1. 將om2m加上https
2. NSCL和GSCL雙向認證時，有介面會顯示出憑證，介面有兩個按鈕，ㄧ個是Verify、ㄧ個Reject

<br><br>
## 未完成
1. 按下按鈕的動作未完成
