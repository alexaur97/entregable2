package converters; 

import org.apache.commons.lang.StringUtils; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.core.convert.converter.Converter; 
import org.springframework.stereotype.Component; 
import org.springframework.transaction.annotation.Transactional; 

import repositories.DropOutRepository; 
import domain.DropOut; 

@Component 
@Transactional 
public class StringToDropOutConverter implements Converter<String, DropOut>{ 

	@Autowired 
	DropOutRepository dropOutRepository; 

	@Override 
	public DropOut convert(String text){ 
		DropOut result; 
		int id; 

		try{ 
			if(StringUtils.isEmpty(text)){ 
				result = null; 
			}else{ 
				id = Integer.valueOf(text); 
				result = dropOutRepository.findOne(id); 
			} 
		}catch (Throwable oops) { 
			throw new IllegalArgumentException(oops); 
		} 

		return result; 
	} 

} 
