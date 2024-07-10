package com.example.practice_eddy.model;

import jakarta.validation.constraints.NotBlank;

public record DisposalBoardDTO(@NotBlank String itemName, @NotBlank String content) { }