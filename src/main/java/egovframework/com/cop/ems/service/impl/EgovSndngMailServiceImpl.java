package egovframework.com.cop.ems.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import egovframework.com.cop.ems.service.EgovSndngMailService;
import egovframework.com.cop.ems.service.SndngMailVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
 * 메일 솔루션과 연동해서 이용해서 메일을 보내는 서비스 구현 클래스
 * @since 2011.09.09
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011.09.09  서준식       최초 작성
 *  
 *  </pre>
 */
@Service("egovSndngMailService")
public class EgovSndngMailServiceImpl extends AbstractServiceImpl implements EgovSndngMailService {
	
	@Resource(name="EMSMailSender")
    private MailSender emsMailSender; 
	
	private static final Logger LOG = Logger.getLogger(EgovSndngMailServiceImpl.class.getClass());
	
	/**
	 * 메일을 발송한다
	 * @param vo SndngMailVO
	 * @return boolean
	 * @exception Exception
	 */
	public boolean sndngMail(SndngMailVO sndngMailVO) throws Exception {

		
		//발신자
		String dsptchPerson = (sndngMailVO.getDsptchPerson() == null) ? "" : sndngMailVO.getDsptchPerson();
		// 수신자
		String recptnPerson = (sndngMailVO.getRecptnPerson() == null) ? "" : sndngMailVO.getRecptnPerson();
		// 메일제목
		String subject = (sndngMailVO.getSj() == null) ? "" : sndngMailVO.getSj();
		// 메일내용
		String emailCn = (sndngMailVO.getEmailCn() == null) ? "" : sndngMailVO.getEmailCn();		
    	
    	SimpleMailMessage msg = new SimpleMailMessage(); 

    	msg.setFrom(dsptchPerson);    
        msg.setTo(recptnPerson);      
        msg.setSubject(subject);     
        msg.setText(emailCn);
        try{
        	this.emsMailSender.send(msg); 
        }catch(MailParseException ex){
        	LOG.error("Sending Mail Exception : " +  ex.getCause() + " [failure when parsing the message]");
        	return false;
        }catch(MailAuthenticationException ex){
        	LOG.error("Sending Mail Exception : " +  ex.getCause() + " [authentication failure]");
        	return false;
        }catch(MailSendException ex){
        	LOG.error("Sending Mail Exception : " +  ex.getCause() + " [failure when sending the message]");
        	return false;
        }catch(Exception ex){
        	LOG.error("Sending Mail Exception : " +  ex.getCause() + " [unknown Exception]");
        	return false;
        }
        
		
		return true;
	}

}
