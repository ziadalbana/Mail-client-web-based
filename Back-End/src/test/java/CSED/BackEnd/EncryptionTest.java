package CSED.BackEnd;


import CSED.BackEnd.Files.FilesUtilities;
import CSED.BackEnd.Files.file;
import CSED.BackEnd.filter.Info;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EncryptionTest {

    @Test
    public void test() throws Exception {
        String notExistUser = "ziad";
        file f = new file();
        assertFalse(f.check("pgassword"));

    }
}
