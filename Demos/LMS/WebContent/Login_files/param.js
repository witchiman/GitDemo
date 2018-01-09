var flag_tag=0;//0: 不显示标签, 1: 允许标签，2:允许标签，但必须审核
var flag_review=0;//0: 不显示评论, 1: 允许评论，2:允许评论，但必须审核
var flag_rating=0;//0: 不显示评级, 1: 显示评级
var flag_recm=1;//0:不显示荐购, 1: 显示荐购
var flag_noanym=1;//0: 允许匿名荐购，1: 不允许匿名荐购
var flag_sug=1;//0:不激活自动补全功能，1: 激活自动补全功能
var flag_idx_hottag=1;//0:不在首页显示推荐标签，1: 在首页显示推荐标签
var flag_toc=1;//0:不显示目次，1: 显示目次
var flag_abs=1;//0:不显示摘要，1: 显示摘要
var flag_zzjj=1;//0:不显示作者简介，1: 显示作者简介
var flag_sfx=1;//0:without sfx，1: with sfx

var clses=[["clc","clc"],["gjc","ddc"],["sci","sci"],["sub","sub"]];//分类法1(中,外), 分类法2(中,外)
var clscode={"clc":"CLC","sci":"OCN","gjc":"GJC","ddc":"WCL","sub":"SUB"};//分类法代码(find_code)

var linkapi={"ext_review":1,"duxiu":1,"shusheng":1,"google":0};//是否显示 读秀/书生/google book 链接

var lkbhost="";

var facetcode={"WYR":"WYR", "WLN":"WLN", "WSL":"WSL", "CLC":"CLC", "WAU":"WAU", "WSU":"WSU", "WFM":"WFT"};//分面检索代码
