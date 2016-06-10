import urllib.request
import urllib.parse
import json
import time
while True:
    url = 'http://fanyi.youdao.com/translate?smartresult=dict&smartresult=rule&smartresult=ugc&sessionFrom=http://www.youdao.com/'
    content = input('输入要翻译的内容(输入q！退出程序)：')
    if content == 'q':
        break
    
    head = {}
    head['User-Agent'] = 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 LBBROWSER'

    data= {}
    data['type'] = 'AUTO'
    data['i'] =content
    data['doctype'] = 'json'
    data['xmlVersion'] = '1.8'
    data['keyfrom'] = 'fanyi.web'
    data['ue'] = 'UTF-8'
    data['action'] = 'FY_BY_CLICKBUTTON'
    data['typoResult'] = 'true'
    data = urllib.parse.urlencode(data).encode('utf-8')

    req = urllib.request.Request(url,data,head)
    '''
    headers第二种方法
    req.add_header('Use-Agent','Mozilla/5.0 (Windows NT 6...')
    '''

    response = urllib.request.urlopen(req)
    html = response.read().decode('utf-8')
    target = json.loads(html)
    print('翻译结果是:%s' % target['translateResult'][0][0]['tgt'])
    time.sleep(7)
