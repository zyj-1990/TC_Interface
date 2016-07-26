package tc.testcase.cunji;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.helper.CommonOperation;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 2016/7/22.
 */
public class ProCreateInfo extends CommonOperation{

    @Test(dataProvider = "data")
    public void proCreateInfo(String msg,String g,String m,String a,String token,String wecha_id,String center) throws Exception{
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
        headers.add(new Parameter("Content-Type","text/html; charset=utf-8"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("g",g));
        paras.add(new Parameter("m",m));
        paras.add(new Parameter("a",a));
        paras.add(new Parameter("token",token));
        paras.add(new Parameter("center",center));
        paras.add(new Parameter("wecha_id",wecha_id));

        Http httpRequest = new Http("get", paras, headers, null);
        String res = "";
        res = HttpRequest.sendRequest_GetHTML(httpRequest,CunjiHost,"/index/php");

        Document doc = Jsoup.parse(res);
    }

    public void VerifyData(String center,String text1,String text2){

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"数据比对有错误","Wap","Savaid","procreateinfo","ed57a8bd-15cc-d9b1-6a1e-97ec43337684","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6","gz","广州医科大学附属第三医院生殖医学中心","广州医学院第三附属医院(原广州市第二人民医院)是有100多年历史的三甲医院，医院妇产科是广东省卫生厅重点学科，历史悠久，全国著名。由广州市政府批准于1989年成立广州市妇产科研究所，属下生殖助孕中心是医院拳头项目之一，是广东省“科教兴医五个一工程”重点专科。2002年我院辅助生殖技术通过了国家卫生部审批，成为可以开展试管婴儿及相关技术的广州首两家医院之一。","生殖医学中心对不孕症的诊治有着悠久的历史，是全国最早开展辅助生殖技术的医疗机构之一。早在1987年在省内率先开展体外授精胚胎移植(IVF-ET)技术的研究工作，于1988年底取得妊娠成功，1989年9月先后诞生了广东省第一、第二例试管婴儿，成为全国第三家取得试管婴儿成功的单位。经过了20年的发展，我院于1994年开展阴道超声监测排卵及取卵术；1995年开展卵巢早衰赠卵试管婴儿术；1996年开展多胎妊娠减胎术。随着我院辅助生殖技术的不断发展成熟，于1999年经广州市卫生局批准特成立生殖助孕科，专门从事不孕不育的工作。2002年我院的辅助生殖技术经过调整、扩大、充实以及进行规范化管理，一举通过了卫生部人类辅助生殖技术资格评审。近几年我科开展了多项新的助孕技术的研究，并相继获得成功。目前我科可开展IVF、ICSI、未成熟卵培养、胚胎冷冻及复苏、睾丸或附睾取精、卵子冷冻、胚胎辅助孵化、囊胚培养等多项助孕技术，种植前胚胎遗传学诊断的相关研究也取得了相应的进展。近五年，在该领域发表文章40余篇，其中SCI 10篇，科研课题立项14项，获科学技术奖1项；2008年门诊接待病人量达到5万人次以上，完成“试管婴儿”治疗人次1500例，其中40%能分娩出健康的宝宝。"},
                {"数据比对有错误","Wap","Savaid","procreateinfo","ed57a8bd-15cc-d9b1-6a1e-97ec43337684","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6","km","昆明市妇女儿童保健中心生殖医学中心（波恩生殖）","波恩生殖中心（总部）位于英国剑桥，由2010年诺贝尔生理学或医学奖获得者、“试管婴儿之父”Robert Edwards创立，是全球首家体外受精试管婴儿治疗中心。至今，波恩生殖中心已有30多年的辅助生殖临床治疗经验，开创了一系列先进的生殖治疗技术，拥有国际顶尖的品牌。2012年其第二代IVF第五天囊胚移植手术成功率在38岁以下产妇中超过60%，经过三个周期的治疗，大于80%的患者能够成功妊娠，在38岁以上高龄产妇中也达到37.5%。同时，世界许多著名的辅助生殖专家都在波恩生殖中心工作或接受过培训。","2012年11月通策医疗投资股份有限公司与英国波恩有限公司达成合作协议，致力于将国际先进的辅助生殖医疗技术和国际标准化管理体系引进中国。2013年5月3日，杭州波恩生殖技术管理有限公司正式成立。该公司主要提供辅助生殖医疗业务有关的咨询服务；它将利用总部的资源，既包括通策医疗的财力、行政和信息技术方面的全力支持，也包括波恩剑桥生殖中心在设计、品牌使用以及技术和管理方面的支持，对各个生殖中心进行高规格建设，对专业人员进行技术和标准化管理培训。杭州波恩生殖技术管理有限公司将不仅致力于建设成功率高效稳定的生殖中心，同时自身也将成为中国辅助生殖领域管理最规范化的集团化管理公司。"},
                {"数据比对有错误","Wap","Savaid","procreateinfo","ed57a8bd-15cc-d9b1-6a1e-97ec43337684","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6","xn","第三军医大学西南医院辅助生殖中心","",""},
                {"数据比对有错误","Wap","Savaid","procreateinfo","ed57a8bd-15cc-d9b1-6a1e-97ec43337684","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6","bj","北京朝阳医院生殖医学中心"," 北京朝阳医院生殖医学中心于1999年9月成立，一直从事妇科内分泌疾病、不孕不育症及相关疾病的专科诊疗工作。现有博士生导师2名，高级职称5名，具有博士学位者9名。生殖医学中心每月接诊生殖内分泌专科病人6000人次以上，每月实施包括体外受精、人工授精等在内的人类辅助生殖技术100余例，体外受精技术的临床妊娠率维持在50-60%。","北京市属综合医院第一例试管婴儿就是在此生殖中心成功，该中心也是目前北京能够采用供精人工授精技术进行辅助生殖的三家单位之一。"},
                {"数据比对有错误","Wap","Savaid","procreateinfo","ed57a8bd-15cc-d9b1-6a1e-97ec43337684","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6","syf","浙大邵逸夫医院生殖中心","p>\n" +
                        "                邵逸夫医院生殖中心始创于1998年，是浙江省最早开展辅助生育技术的综合性医院之一，2005年中心顺利通过国家卫生部资格认证。中心拥有一支高素质的诊疗队伍，新鲜移植周期的临床妊娠率持续保持在45％左右，冻融胚胎移植周期的临床妊娠率在50％左右。国内首例子宫内膜癌患者经我中心保守治疗和IVF-ET技术获得妊娠及分娩。较高的成功率和本中心医务人员尽职尽责的服务在广大不孕不育患者中树立了良好的口碑。目前开展的辅助生殖项目有：","1．试管婴儿（即体外授精—胚胎移植，IVF-ET）<br>\n" +
                        "                2．卵细胞浆内单精子显微注射（ICSI）<br>\n" +
                        "                3. 夫精人工授精 （AIH）<br>\n" +
                        "                4. 胚胎冷冻保存<br>\n" +
                        "                5．囊胚培养和冷冻保存<br>\n" +
                        "                6．经皮附睾或睾丸精子抽吸术+单精子卵胞浆内注射<br>\n" +
                        "                7．胚胎辅助孵化（AH）<br>\n" +
                        "                8．经阴道减胎术\n" +
                        "            </p>\n" +
                        "            <p>另外还开展与不孕相关的各类腹腔镜、宫腔镜诊治（详见前面妇科关于腹腔镜、宫腔镜手术项目的介绍）及经阴道改良子宫颈环扎术。"},
                {"数据比对有错误","Wap","Savaid","procreateinfo","ed57a8bd-15cc-d9b1-6a1e-97ec43337684","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6","sh","上海国妇婴生殖中心","中国福利会国际和平妇幼保健院辅助生殖医学中心成立于2000年5月。2002年11月，经国家卫生部专家组严格审核，批准同意开展体外授精-胚胎移植(IVF)、卵胞浆内单精子显微注射技术(ICSI)。成为上海市首批荣获卫生部批准的辅助生殖医学中心。2004年3月通过上海市卫生局审批，批准同意开展夫精人工授精技术。","中国福利会国际和平妇幼保健院是国家名誉主席宋庆龄，于1952年以她荣获的“加强国际和平”斯大林国际奖金所创建并命名的一所妇产科专科医院。医院分设围产科(产科、新生儿科)、妇科(一般妇科、肿瘤科、乳腺科)、计划生育科、中医科、麻醉科和病理、药剂、检验、放射、B超等辅助科室。生殖医学中心依托国际和平妇幼保健院临床部的医疗资源、医疗环境和辅助科室的各项先进的检测手段，顺利的开展各项工作，成为辅助生殖医学中心的坚强后盾。丰富了中心诊疗不孕与不育的方法和手段。","中心贯彻“以人为本，以病人为中心”的服务模式，以卫生部颁发的“人类辅助生殖技术管理办法”和“人类辅助生殖技术和人类精子库技术规范、基本标准及伦理原则”为准则，以一流的设备、规范的管理、优良的服务，为广大不孕症夫妇提供辅助生殖技术服务。中心首例IVF婴儿诞生于2001年2月;首例ICSI婴儿诞生于2001年4月，首例FET婴儿诞生于2001年12月。先后为高龄绝经、卵巢早衰、原发闭经、不明原因不孕、多囊卵巢综合症、子宫内膜异位症等疑难病症的妇女获得了健康的试管婴儿。现在中心每年进行600例“试管婴儿”的治疗，临床妊娠率达到 40% 左右。","中心的宗旨是：母亲安全、宝宝健康;中心的承诺是：以先进的技术，优质的服务和合理价格为不孕不育夫妇排忧解难;中心的目标：临床与科研相结合，成功率达国际先进水平，成为全国一流的辅助生殖医学中心。"},
                {"数据比对有错误","Wap","Savaid","procreateinfo","ed57a8bd-15cc-d9b1-6a1e-97ec43337684","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6","cd","","",""},
                {"数据比对有错误","Wap","Savaid","procreateinfo","ed57a8bd-15cc-d9b1-6a1e-97ec43337684","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6","bn","","",""},
        };
        return data;
    }
}
