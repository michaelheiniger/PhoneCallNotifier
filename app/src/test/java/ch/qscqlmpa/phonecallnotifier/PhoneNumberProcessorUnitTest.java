package ch.qscqlmpa.phonecallnotifier;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.phonecall.PhoneNumberProcessor;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class PhoneNumberProcessorUnitTest {

    @Before
    public void setup() {
        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void formatTranslationTest() {

        String phoneNumber = "+41787772343";
        assertEquals(true, PhoneNumberProcessor.doesPhoneNumberMatchFormat(phoneNumber," 0041 78 777 23 43 "));
        assertEquals(true, PhoneNumberProcessor.doesPhoneNumberMatchFormat(phoneNumber,"00417877723##"));
        assertEquals(true, PhoneNumberProcessor.doesPhoneNumberMatchFormat(phoneNumber,"004178###2343"));
        assertEquals(true, PhoneNumberProcessor.doesPhoneNumberMatchFormat(phoneNumber,"#04178##723#3"));
        assertEquals(true, PhoneNumberProcessor.doesPhoneNumberMatchFormat(phoneNumber,"#############"));
        assertEquals(true, PhoneNumberProcessor.doesPhoneNumberMatchFormat(phoneNumber,"0041#########"));
        assertEquals(true, PhoneNumberProcessor.doesPhoneNumberMatchFormat(phoneNumber,"+41#########"));
        assertEquals(true, PhoneNumberProcessor.doesPhoneNumberMatchFormat(phoneNumber,"+41a78 7$77!23_4-3"));
    }

    @Test
    public void multipleFormatsTest() {
        String phoneNumber = "+41787772343";
        List<String> formats = Arrays.asList(
                "0041787772343",
                "###",
                "#####################",
                "12341341",
                "1233211332111");

        assertEquals(true, PhoneNumberProcessor.doesPhoneNumberMatchAtLeastOneFormat(phoneNumber,formats));
        assertEquals("There is not format: test should fail", false, PhoneNumberProcessor.doesPhoneNumberMatchAtLeastOneFormat(phoneNumber,Arrays.asList()));
    }
}
