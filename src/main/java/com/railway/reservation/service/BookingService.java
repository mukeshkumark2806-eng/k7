package com.railway.reservation.service;

import com.railway.reservation.dto.request.BookingRequestDto;
import com.railway.reservation.dto.response.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDto requestDto);
    List<BookingResponseDto> getAllBookings();
    BookingResponseDto getBookingById(Long id);
    BookingResponseDto getBookingByPnr(String pnrNumber);
    List<BookingResponseDto> getBookingsByPassengerId(Long passengerId);
    List<BookingResponseDto> getBookingsByTrainId(Long trainId);
    BookingResponseDto cancelBooking(Long id);
    void deleteBooking(Long id);
}
