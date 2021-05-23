//package com.cda.cyberpik.service;
//
//import com.cda.cyberpik.dao.IRepositoryPhoto;
//import com.cda.cyberpik.dto.PhotoDto;
//import com.cda.cyberpik.entity.Photo;
//import com.cda.cyberpik.entity.Transformation;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class PhotoServiceTest {
//    @Mock
//    private ModelMapper modelMapper;
//
//    @Mock
//    private IRepositoryPhoto photoDao;
//
//    @InjectMocks
//    private PhotoService photoService;
//
//    @Test
//    void shouldReturnGetAll() {
//        // Context
//        Photo p1 = new Photo();
//        p1.setPhotoId(1L);
//        p1.setTitle("image_1");
//        p1.setPhotoTransformations(new ArrayList<Transformation>());
//
//        Photo p2 = new Photo();
//        p2.setPhotoId(2L);
//        p2.setTitle("image_2");
//        p2.setPhotoTransformations(new ArrayList<Transformation>());
//
//        List<Photo> photos = new ArrayList<>();
//        photos.add(p1);
//        photos.add(p2);
//
//         PhotoDto photoDto = new PhotoDto();
//
//        // Action
//        when(modelMapper.map(any(), any())).thenReturn(photoDto);
//        when(photoDao.findAll()).thenReturn(photos);
//        List<PhotoDto> actual = photoService.getAll();
//
//        // Result
//        assertEquals(2, actual.size());
//        verify(photoDao, times(1)).findAll();
//    }
//}
