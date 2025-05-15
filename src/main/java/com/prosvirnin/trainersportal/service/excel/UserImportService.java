package com.prosvirnin.trainersportal.service.excel;

import com.prosvirnin.trainersportal.model.domain.user.Gender;
import com.prosvirnin.trainersportal.model.dto.user.UserImportRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserImportService {

    public List<UserImportRow> parseExcel(MultipartFile file){
        List<UserImportRow> rows = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream()) {
        }) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                UserImportRow user = new UserImportRow();
                int count = 0;
                user.setFullName(getCell(row, count++));
                user.setPhoneNumber(getCell(row, count++));
                user.setBirthDate(parseDate(getCell(row, count++)));
                user.setCity(getCell(row, count++));
                user.setKyu(parseInt(getCell(row, count++)));
                user.setAttestationDate(parseDate(getCell(row, count++)));
                user.setAnnualFee(parseInt(getCell(row, count++)));
                user.setGender(Gender.valueOf(getCell(row, count++).toUpperCase()));
                user.setSchoolClass(parseInt(getCell(row, count++)));
                user.setParentName(getCell(row, count++));
                user.setParentPhone(getCell(row, count++));
                user.setLogin(getCell(row, count++));
                user.setPassword(getCell(row, count));

                rows.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rows;
    }

    private String getCell(Row row, int index) {
        Cell cell = row.getCell(index);
        return cell != null ? cell.toString().trim() : "";
    }

    private LocalDate parseDate(String value) {
        return value == null || value.isBlank() ? null : LocalDate.parse(value);
    }

    private Integer parseInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
