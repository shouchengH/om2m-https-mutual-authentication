"# om2m-https-mutual-authentication" 

create_self-signed-GSCL.py : 利用Python來創造GSCL的Keystore，存放在PKI資料夾下<br>
create_self-signed-NSCL.py : 利用Python來創造NSCL的Keystore ，存放在PKI資料夾下<br>
start_GSCL.bat : 開啟GSCL的start.bat檔，內容為C:\Users\cheng\git\org.eclipse.om2m\org.eclipse.om2m.site.gscl\target\products\gscl\win32\win32\x86\，因為是絕對路徑，會需要修改路徑<br>
start_NSCL.bat : 開啟NSCL的start.bat檔，內容為C:\Users\cheng\git\org.eclipse.om2m\org.eclipse.om2m.site.nscl\target\products\nscl\win32\win32\x86\，因為是絕對路徑，會需要修改路徑<br>


$ git clone https://github.com/shouchengH/om2m-https-mutual-authentication<br>
$ cd org.eclipse.om2m<br>
$ mvn install<br>
