/*
 * Copyright 2020 White Magic Software, Ltd.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.keenwrite;

import java.nio.file.Path;

import static com.keenwrite.Constants.GLOB_PREFIX_FILE;
import static com.keenwrite.Constants.SETTINGS;
import static com.keenwrite.FileType.UNKNOWN;
import static com.keenwrite.predicates.PredicateFactory.createFileTypePredicate;

/**
 * Provides common behaviours for factories that instantiate classes based on
 * file type.
 */
public abstract class AbstractFileFactory {

  /**
   * Determines the file type from the path extension. This should only be
   * called when it is known that the file type won't be a definition file
   * (e.g., YAML or other definition source), but rather an editable file
   * (e.g., Markdown, XML, etc.).
   *
   * @param path The path with a file name extension.
   * @return The FileType for the given path.
   */
  public static FileType lookup( final Path path ) {
    return lookup( path, GLOB_PREFIX_FILE );
  }

  /**
   * Creates a file type that corresponds to the given path.
   *
   * @param path   Reference to a variable definition file.
   * @param prefix One of GLOB_PREFIX_DEFINITION or GLOB_PREFIX_FILE.
   * @return The file type that corresponds to the given path.
   */
  protected static FileType lookup( final Path path, final String prefix ) {
    assert path != null;
    assert prefix != null;

    final var keys = SETTINGS.getKeys( prefix );

    var found = false;
    var fileType = UNKNOWN;

    while( keys.hasNext() && !found ) {
      final var key = keys.next();
      final var patterns = SETTINGS.getStringSettingList( key );
      final var predicate = createFileTypePredicate( patterns );

      if( found = predicate.test( path.toFile() ) ) {
        // Remove the EXTENSIONS_PREFIX to get the filename extension mapped
        // to a standard name (as defined in the settings.properties file).
        final String suffix = key.replace( prefix + ".", "" );
        fileType = FileType.from( suffix );
      }
    }

    return fileType;
  }
}
