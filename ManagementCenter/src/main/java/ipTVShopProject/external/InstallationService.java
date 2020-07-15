
package ipTVShopProject.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

//@FeignClient(name="Installation", url="http://localhost:8083")  //url="http://Installation:8080")
@FeignClient(name="Installation", url="http://Installation:8080")
public interface InstallationService {

    @RequestMapping(method= RequestMethod.POST, path="/installations")
    public void installationCancellation(@RequestBody Installation installation);

}
