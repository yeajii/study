package com.oracle.oBootMybatis01.controller;

import org.springframework.stereotype.Controller;

import com.oracle.oBootMybatis01.service.CommentsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentsController {
	
	private final CommentsService cs;
	
}
