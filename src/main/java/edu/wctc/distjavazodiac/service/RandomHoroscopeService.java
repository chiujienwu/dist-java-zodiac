package edu.wctc.distjavazodiac.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wctc.distjavazodiac.entity.Birthday;
import edu.wctc.distjavazodiac.entity.Fortune;
import edu.wctc.distjavazodiac.entity.Horoscope;
import edu.wctc.distjavazodiac.entity.Month;
import edu.wctc.distjavazodiac.repo.FortuneRepository;
import edu.wctc.distjavazodiac.repo.MonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Random horoscopes from https://cafeastrology.com/dailyhoroscopesall-tomorrow.html
 */
@Service
public class RandomHoroscopeService implements HoroscopeService {

    private List<Fortune> allFortunes;
    private ZodiacService zodiacService;

    @Autowired
    public RandomHoroscopeService(ZodiacService zodiacService) {
        this.zodiacService = zodiacService;
    }

    @Override
    public Horoscope getHoroscope(Birthday birthday) {
        String sign;
        if (birthday.getZodiacType().toLowerCase().startsWith("w")) {
            sign = zodiacService.getWesternZodiacSign(birthday);
        } else {
            sign = zodiacService.getEasternZodiacSign(birthday);
        }

        Horoscope hscope = new Horoscope();
        hscope.setSign(sign);

        int randomIndex = (int) (Math.random() * allFortunes.size());
        hscope.setHoroscope(allFortunes.get(randomIndex).getText());
        return hscope;
    }

    // TODO Using the schema, modify the code to get data from database instead of JSOn files

    @Autowired
    private FortuneRepository fortuneRepository;

    @PostConstruct
    public void initHoroscopes() {

//        public List<Fortune> getFortune() {
//            List<Fortune> list = new ArrayList<>();
//            fortuneRepository.findAll().forEach(list::add);
//            return list;
//        }

        List<Fortune> list = new ArrayList<>();
        fortuneRepository.findAll().forEach(list::add);
        allFortunes = list;

//        ObjectMapper mapper = new ObjectMapper();
//
//        Fortune[] fortuneArray;
//        try {
//            fortuneArray = mapper.readValue(Paths.get("fortunes.json").toFile(), Fortune[].class);
//        } catch (IOException e) {
//            e.printStackTrace();
//            fortuneArray = new Fortune[]{new Fortune()};
//        }
//        allFortunes = Arrays.asList(fortuneArray);
    }
}
