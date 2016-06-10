
import re
import urllib.request
import os

def open_url(url):
    headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 LBBROWSER'}
    req = urllib.request.Request(url,None,headers)
    response = urllib.request.urlopen(req)
    html = response.read().decode('utf-8')
    return html

def get_img(html):
    if os.path.exists('MM'):
         os.chdir('MM')
    else:
        os.mkdir('MM')
        os.chdir('MM')
    p = r'<img class="BDE_Image" src="([^"]+\.jpg)"'
    img_addrs = re.findall(p,html)
    for each in img_addrs:
        filename = each.split('/')[-1]
        urllib.request.urlretrieve(each,filename,None) 


    
if __name__ =='__main__':
    url ='http://tieba.baidu.com/p/2175116817?see_lz=1&pn=3'
    get_img(open_url(url))
