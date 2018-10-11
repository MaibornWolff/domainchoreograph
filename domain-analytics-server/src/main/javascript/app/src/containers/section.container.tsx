import { withTheme } from 'emotion-theming';
import * as React from 'react';
import { Section, SectionProps } from '~components/section/section';

export const SectionContainer: React.StatelessComponent<Omit<SectionProps, 'theme'>> = withTheme(Section) as any;
