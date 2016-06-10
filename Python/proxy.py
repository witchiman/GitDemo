import urllib.request
import random
url = 'http://www.whatismyip.com.tw'
iplist = ['117.168.236.79','117.165.137.146','127.141.8.1','221.181.6.52']
proxy_support = urllib.request.ProxyHandler({'http':random.choice(iplist)})

opener = urllib.request.build_opener(proxy_support)
opener.addheaders = [('User-Agent','Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 LBBROWSER')]

urllib.request.install_opener(opener)

response = urllib.request.urlopen(url)

html  = response.read().deconde('utf-8')

print(html)
