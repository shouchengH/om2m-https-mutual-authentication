# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this 
# file, You can obtain one at http://mozilla.org/MPL/2.0/.

# password = 123456

import sys
import os
import shutil
import socket

'''
if len(sys.argv) < 2:
    sys.exit('Usage: %s directory to output certificates' % sys.argv[0])

if not os.path.exists(sys.argv[1]):
    sys.exit('ERROR: Directory %s was not found!' % sys.argv[1])
'''
	
os.chdir(os.path.dirname(os.path.abspath(__file__)))

os.environ['HOSTNAME'] = socket.gethostname()
os.environ['OPENSSL_CONF'] = os.path.join(os.getcwd(), "localhost.cnf")

os.system("""openssl genrsa -out PKI/nscl.key 2048""")
os.system("""openssl req \
	-x509 \
	-new \
	-nodes \
	-key PKI/nscl.key \
	-days 3650 \
	-subj "/C=DE/O=nscl/CN=nscl.org" \
	-out PKI/nscl.crt""")
os.system("""openssl req \
    -new \
    -newkey rsa:2048 \
    -nodes \
    -subj "/C=DE/O=nscl/CN=nsclServer@localhost" \
    -config localhost.cnf \
    -keyout PKI/localhost.key \
    -out PKI/localhost.csr""")
os.system("""openssl x509 -req \
	-days 3650 \
	-in PKI/localhost.csr \
	-CA PKI/nscl.crt \
	-CAkey PKI/nscl.key \
	-CAcreateserial \
	-out PKI/localhost.crt \
	-extensions v3_ca \
	-extfile localhost.cnf""")
os.system("""openssl x509 -in PKI/localhost.crt -outform der -out PKI/server_cert.der""")
#we will need these files later
os.remove("PKI/localhost.key") #we will need it later
os.remove("PKI/localhost.crt")
os.remove("PKI/localhost.csr")
#os.remove("PKI/nscl.key")
os.remove("PKI/nscl.srl")
os.remove("PKI/server_cert.der")

os.system("""keytool -keystore PKI/NSCL.keystore -import -alias nscl -file PKI/nscl.crt -trustcacerts""")
os.system("""openssl pkcs12 -inkey PKI/nscl.key -in PKI/nscl.crt -export -out PKI/nscl.pkcs12""")
os.system("""keytool -importkeystore -srckeystore PKI/nscl.pkcs12 -srcstoretype PKCS12 -destkeystore PKI/NSCL.keystore""")