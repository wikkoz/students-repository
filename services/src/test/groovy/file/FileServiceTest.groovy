package file

import com.services.file.FileService
import spock.lang.Specification


class FileServiceTest extends Specification{
    FileService fileService;

    def setup() {
        fileService = new FileService();
    }

    def "should correctly compare heads"() {
        given:
        String s = "abc,bcd"
        List<String> list = ["abc","bcd"]
        when:
        fileService.compareHeaders(list, s)
        then:
        true;

    }


    def "should throw expection when compare heads are diffrent"() {
        given:
        String s = "abc,bcdf"
        List<String> list = ["abc","bcd"]
        when:
        fileService.compareHeaders(list, s)
        then:
        thrown IllegalArgumentException;

    }
}
