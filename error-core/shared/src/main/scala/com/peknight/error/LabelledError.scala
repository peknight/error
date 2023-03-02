package com.peknight.error

trait LabelledError extends Error:
  def label: String
