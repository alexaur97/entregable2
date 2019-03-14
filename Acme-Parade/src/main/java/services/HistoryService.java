
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.HistoryRepository;

@Service
@Transactional
public class HistoryService {

	@Autowired
	private HistoryRepository	historyRepository;

}
