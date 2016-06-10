#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from socket import *
from time import ctime

BUFSIZE = 1024
ADDR = ('localhost', 3456)
s = socket(AF_INET, SOCK_DGRAM)
while True:
    data = input('please input the data:')
    if not data:
        break
    s.sendto(data.encode('utf8'), ADDR)
    data,addr = s.recvfrom(BUFSIZE)
    data = data.decode()  #默认是utf8
    if not data:
        break
    print(data)
s.close()
