

import socket
import sys
import time
import urllib
import urllib2
import time
import serial
import pprp
import base64

KEY_SIZE = 16
BLOCK_SIZE = 32

def encrypt(key, plaintext):
    key = key.encode('ascii')
    plaintext = plaintext.encode('utf-8')
    padded_key = key.ljust(KEY_SIZE, b'\0')

    sg = pprp.data_source_gen(plaintext, block_size=BLOCK_SIZE)
    eg = pprp.rjindael_encrypt_gen(padded_key, sg, block_size=BLOCK_SIZE)

    ciphertext = pprp.encrypt_sink(eg)

    encoded = base64.b64encode(ciphertext)

    return encoded.decode('ascii')


def decrypt(key, encoded):
    key = key.encode('ascii')
    padded_key = key.ljust(KEY_SIZE, b'\0')

    ciphertext = base64.b64decode(encoded.encode('ascii'))

    sg = pprp.data_source_gen(ciphertext, block_size=BLOCK_SIZE)
    dg = pprp.rjindael_decrypt_gen(padded_key, sg, block_size=BLOCK_SIZE)

    return pprp.decrypt_sink(dg).decode('utf-8')


key = 'S@ftware12345678'
#ttyACM0
ser=serial.Serial('/dev/ttyACM0',115200)
while True:
    read_serial=ser.readline()
    textlist = list(read_serial)
    if textlist[0] == "4":
                passwd = textlist[1:5]
                password = ''.join(passwd)
                passwordenc=encrypt(key, password)
                station = textlist[5:-2]
                stat = ''.join(station)
                query_args = {'pass': passwordenc, 'station': stat}
                query_stat = {'stat': stat}
                encoded_args = urllib.urlencode(query_args)
                encoded_stat = urllib.urlencode(query_stat)

                url = 'https://paperback-chimpanzee-1561.dataplicity.io/bicycle/passwordcheckborrow.php?' + encoded_args
                

                # time.sleep(3)
                req = urllib2.Request(url)

                response = urllib2.urlopen(req)

                result = response.read()
                url2 = 'https://paperback-chimpanzee-1561.dataplicity.io/bicycle/passwordverifyborrow.php?' + encoded_stat
                

                # time.sleep(3)
                req2 = urllib2.Request(url2)

                response2 = urllib2.urlopen(req2)

                result2 = response2.read()

                dock = result2[1:-1]
                print (dock)  # echo
                ser.write(dock)
    elif textlist[0] == "5":
                passwd = textlist[1:5]
                password = ''.join(passwd)
                passwordenc=encrypt(key, password)
                doc = textlist[5]
                j = 5
                while (doc != 'D'):
                    j += 1
                    doc = textlist[j]

                dok = textlist[5:j]
                dock = ''.join(dok)
                j += 1
                station = textlist[j:-2]
                stat = ''.join(station)

                query_args = {'pass': passwordenc, 'station': stat, 'dock': dock}
                query_stat = {'stat': stat}
                encoded_args = urllib.urlencode(query_args)
                encoded_stat = urllib.urlencode(query_stat)

                url = 'https://paperback-chimpanzee-1561.dataplicity.io/bicycle/passwordcheckpark.php?' + encoded_args
                req = urllib2.Request(url)

                response = urllib2.urlopen(req)

                result = response.read()
                # time.sleep(3)
                url2 = 'https://paperback-chimpanzee-1561.dataplicity.io/bicycle/passwordverifypark.php?' + encoded_stat
                req2 = urllib2.Request(url2)

                response2 = urllib2.urlopen(req2)

                result2 = response2.read()
                


                

                dock = result2[1:-1]
                print(dock)
                ser.write(dock)

