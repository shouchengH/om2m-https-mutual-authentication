# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this 
# file, You can obtain one at http://mozilla.org/MPL/2.0/.

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

os.system("""openssl genrsa -out postman.key 2048""")
os.system("""openssl req \
	-x509 \
	-new \
	-nodes \
	-key postman.key \
	-days 3650 \
	-subj "/C=DE/O=postman/CN=postman.org" \
	-out postman.crt""")
