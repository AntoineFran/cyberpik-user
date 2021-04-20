package com.cda.cyberpik.service;

import com.cda.cyberpik.dao.IRepositoryPhoto;
import com.cda.cyberpik.entity.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PhotoServiceTest {

    @Mock
    IRepositoryPhoto photoDao;

    @Autowired
    @InjectMocks
    PhotoService photoService;

    @BeforeEach
    void setMockOutput() {
        when(photoDao.findAll()).thenReturn(new ArrayList<Photo>());
    }

    @DisplayName("Test Mock photoService + helloRepository")
    @Test
    void testFindAll() {
        assertEquals(new ArrayList<Photo>(), photoDao.findAll());
    }
}
